package com.example.martin.service.dto;


import java.util.List;

public class ListResponse {

  private List<?> data;
  private int totalPages;
  private int totalItemsInPage;
  private long totalElements;

  public List<?> getData() {
    return data;
  }

  public void setData(List<?> data) {
    this.data = data;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getTotalItemsInPage() {
    return totalItemsInPage;
  }

  public void setTotalItemsInPage(int totalItemsInPage) {
    this.totalItemsInPage = totalItemsInPage;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }
}
