# Project Documentation

## Table of Contents

- [1. Introduction](#1-introduction)

- [2. Customer Requirements](#2-customer-requirements)

- [3. Development Methods](#3-development-methods)

- [4. System Design and Architecture](#4-system-design-and-architecture)

- [5. Implementation](#5-implementation)

    - [5.1 UI](#51-ui)

    - [5.2 Database](#52-database)

- [6. QA](#6-qa)

    - [6.1 Testing](#61-testing)

    - [6.2 Clean Code](#62-clean-code)

- [7. Summary](#7-summary)



# 1. Introduction





# 2. Customer Requirements





# 3. Development Methods





# 4. System Design and Architecture





# 5. Implementation



## 5.1 UI



## 5.2 Database
```sh
create database invsys;

use invsys;

create table Category
(
    Id   int auto_increment
        primary key,
    Name varchar(255) null,
    constraint Name
        unique (Name)
);

create table Suppliers
(
    SupplierID   int auto_increment
        primary key,
    Name         varchar(255) null,
    ContactEmail varchar(255) null,
    constraint ContactEmail
        unique (ContactEmail)
);

create table Products
(
    ProductID    int auto_increment
        primary key,
    Name         varchar(255)   null,
    Price        decimal(38, 2) null,
    Quantity     int            not null,
    SupplierID   int            not null,
    CategoryId   int            null,
    CategoryName varchar(255)   null,
    SupplierName varchar(255)   null,
    constraint FK_Category
        foreign key (CategoryId) references Category (Id),
    constraint Products_ibfk_1
        foreign key (SupplierID) references Suppliers (SupplierID)
);

create index SupplierID
    on Products (SupplierID);

create table Users
(
    UserId      int auto_increment
        primary key,
    username    varchar(255) null,
    FirstName   varchar(255) null,
    LastName    varchar(255) null,
    Password    varchar(255) not null,
    AccessLevel varchar(255) null,
    constraint Username
        unique (username)
);

create table Transactions
(
    TransactionID   int auto_increment
        primary key,
    ProductID       int          not null,
    TransactionDate datetime(6)  null,
    Quantity        int          not null,
    TransactionType varchar(255) null,
    UserId          int          not null,
    constraint Transactions_ibfk_1
        foreign key (ProductID) references Products (ProductID),
    constraint Transactions_ibfk_2
        foreign key (UserId) references Users (UserId)
);

create index ProductID
    on Transactions (ProductID);

create index UserId
    on Transactions (UserId);
```
# 6. QA



## 6.1 Testing



## 6.2 Clean Code





# 7. Summary

