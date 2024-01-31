package com.example.product.testUtil;

import com.example.product.core.domain.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(UUID.fromString(resultSet.getString("id")));
        product.setName(resultSet.getString("name"));
        product.setAuthor(resultSet.getString("author"));
        product.setPublisher(resultSet.getString("publisher"));
        product.setDescription(resultSet.getString("description"));
        product.setImgUrl(resultSet.getString("imgurl"));
        product.setPublishYear(resultSet.getInt("publishyear"));
        product.setPages(resultSet.getInt("pages"));
        product.setPrice(resultSet.getDouble("price"));

        return product;
    }
}