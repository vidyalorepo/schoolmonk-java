package com.dcc.schoolmonk.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity(name = "TRANSACTION")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "order_id")
  String orderId;

  @Column(name = "txn_ts")
  LocalDateTime txnTs;

  @Column(name = "bank_ref_no")
  String bankRefNo;

  @Column(name = "order_status")
  String orderStatus;

  @Column(name = "currency")
  String currency;

  @Column(name = "amount")
  BigDecimal amount;

  @Column(name = "payment_mode")
  String paymentMode;

  @Column(name = "card_name")
  String cardName;

  public Transaction(
      String orderId,
      LocalDateTime txnTs,
      String bankRefNo,
      String orderStatus,
      String currency,
      BigDecimal amount,
      String paymentMode,
      String cardName) {
    this.orderId = orderId;
    this.txnTs = txnTs;
    this.bankRefNo = bankRefNo;
    this.orderStatus = orderStatus;
    this.currency = currency;
    this.amount = amount;
    this.paymentMode = paymentMode;
    this.cardName = cardName;
  }

  public Transaction() {}

  public Long getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public LocalDateTime getTxnTs() {
    return txnTs;
  }

  public String getBankRefNo() {
    return bankRefNo;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getPaymentMode() {
    return paymentMode;
  }

  public String getCardName() {
    return cardName;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public void setTxnTs(LocalDateTime txnTs) {
    this.txnTs = txnTs;
  }

  public void setBankRefNo(String bankRefNo) {
    this.bankRefNo = bankRefNo;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setPaymentMode(String paymentMode) {
    this.paymentMode = paymentMode;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }
  
}
