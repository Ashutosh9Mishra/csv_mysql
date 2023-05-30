package com.csv_mysql.Process;


import com.csv_mysql.Model.Order;
import org.springframework.batch.item.ItemProcessor;

//processing the item
public class OrderItemProcessor implements ItemProcessor<Order, Order> {
    @Override
    public Order process(Order order) {
        String phoneNumber = order.getPhoneNumber();
        String country = determineCountry(phoneNumber);
        order.setCountry(country);
        return order;
    }
// determing the country on the basis of phone number
    private String determineCountry(String phoneNumber) {
        if (phoneNumber.matches("\\(237\\)\\ ?[2368]\\d{7,8}$")) {
            return "Cameroon";
        } else if (phoneNumber.matches("\\(251\\)\\ ?[1-59]\\d{8}$")) {
            return "Ethiopia";
        } else if (phoneNumber.matches("\\(212\\)\\ ?[5-9]\\d{8}$")) {
            return "Morocco";
        } else if (phoneNumber.matches("\\(258\\)\\ ?[28]\\d{7,8}$")) {
            return "Mozambique";
        } else if (phoneNumber.matches("\\(256\\)\\ ?\\d{9}$")) {
            return "Uganda";
        }
        return "Unknown Country";
    }
}
