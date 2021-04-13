package com.example.batch.batchdemo

class Product {

    private String productId
    private String name
    private String description
    private String price

    String getProductId() {
        return productId
    }

    void setProductId(String productId) {
        this.productId = productId
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getPrice() {
        return price
    }

    void setPrice(String price) {
        this.price = price
    }
}
