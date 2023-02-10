package com.link_intersystems.film.listing;

import com.link_intersystems.film.Category;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class FindCategoryAnswer implements Answer<Category> {
    @Override
    public Category answer(InvocationOnMock invocationOnMock) throws Throwable {
        String categoryName = invocationOnMock.getArgument(0, String.class);
        return Category.valueOf(categoryName);
    }
}
