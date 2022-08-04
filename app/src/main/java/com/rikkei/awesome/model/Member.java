package com.rikkei.awesome.model;

import java.io.Serializable;

public class Member implements Serializable {
        private String id, user1, user2;

        public Member() {
        }

        public Member(String user1, String user2) {
                this.user1 = user1;
                this.user2 = user2;
        }

        public String getUser1() {
                return user1;
        }

        public void setUser1(String user1) {
                this.user1 = user1;
        }

        public String getUser2() {
                return user2;
        }

        public void setUser2(String user2) {
                this.user2 = user2;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }
}