package com.nb.user.model;

/**
 * @description 订单
 * @author: fly
 * @date: 2019/1/22 9:51
 */
public class Order {

    private Long orderId;
    private Long userId;
    private Long shopId;

    public Order() {}

    private static class Builder {
        private Long orderId;
        private Long userId;
        private Long shopId;

        public Builder orderId(Long val) {
            this.orderId = val;
            return this;
        }

        public Builder userId(Long val) {
            this.userId = val;
            return this;
        }

        public Builder shopId(Long val) {
            this.shopId = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.shopId = builder.shopId;
    }
}