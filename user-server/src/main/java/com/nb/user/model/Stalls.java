package com.nb.user.model;

import java.sql.Timestamp;

public class Stalls {
    private Long id;
    private String name;
    private Timestamp createTime;
    private Byte delete;

    public static class Builder {
        private Long id;
        private String name;
        private Timestamp createTime;
        private Byte delete;

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder createTime(Timestamp val) {
            createTime = val;
            return this;
        }

        public Builder delete(Byte val) {
            delete = val;
            return this;
        }

        public Stalls build() {
            return new Stalls(this);
        }
    }

    private Stalls(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.createTime = builder.createTime;
        this.delete = builder.delete;
    }

    @Override
    public String toString() {
        return "Stalls{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", delete=" + delete +
                '}';
    }
}
