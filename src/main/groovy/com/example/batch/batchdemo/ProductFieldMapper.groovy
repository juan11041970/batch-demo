package com.example.batch.batchdemo

import net.bytebuddy.description.modifier.FieldManifestation
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.validation.BindException

class ProductFieldMapper implements FieldSetMapper<Product> {
    @Override
    Product mapFieldSet(FieldSet fieldSet) throws BindException {
        Product product = new Product()
        product.setId(fieldSet.readString("productid"))
        product.setName(fieldSet.readString("name"))
        product.setDescription(fieldSet.readString("description"))
        product.setPrice(fieldSet.readString("price"))
        return product
    }
}
