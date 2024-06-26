package com.shopstream.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shopstream.exception.OrderException;
import com.shopstream.model.*;
import com.shopstream.repository.AddressRepository;
import com.shopstream.repository.OrderItemRepository;
import com.shopstream.repository.OrderRepository;
import com.shopstream.repository.UserRepository;
import com.shopstream.user.domain.OrderStatus;
import com.shopstream.user.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippAddress) {

        shippAddress.setUser(user);

        Address existingAddress = addressRepository.findByAddressId(shippAddress.getId());

        if (existingAddress == null) {
            existingAddress = addressRepository.save(shippAddress);
            user.getAddresses().add(existingAddress);
        }

//        Address address= addressRepository.save(shippAddress);
//        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart=cartService.findUserCart(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem item: cart.getCartItems()) {
            OrderItem orderItem=new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());


            OrderItem createdOrderItem=orderItemRepository.save(orderItem);

            orderItems.add(createdOrderItem);
        }


        Order createdOrder=new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(existingAddress);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder=orderRepository.save(createdOrder);

        for(OrderItem item:orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;

    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);


        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt=orderRepository.findById(orderId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new OrderException("order not exist with id "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders=orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order =findOrderById(orderId);

        orderRepository.deleteById(orderId);

    }

}

