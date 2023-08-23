# Overview


![GitHub](https://img.shields.io/badge/android-%23326ce5.svg?style=for-the-badge&logo=android&logoColor=white)
![GitHub](https://img.shields.io/badge/java-%23006d77.svg?style=for-the-badge&logo=java&logoColor=white)
![GitHub](https://img.shields.io/badge/sqlite-%23ff8fab.svg?style=for-the-badge&logo=sqlite&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![GitHub](https://img.shields.io/badge/kotlin-%23f72585.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![GitHub](https://img.shields.io/badge/lottie-%23f7a072.svg?style=for-the-badge&logo=lottie&logoColor=white)
![GitHub](https://img.shields.io/badge/mvvm-%23e0fbfc.svg?style=for-the-badge&logo=mvvm&logoColor=black)
![GitHub](https://img.shields.io/badge/room-%23ee6c4d.svg?style=for-the-badge&logo=room&logoColor=white)
![GitHub](https://img.shields.io/badge/lifecycle-%23e7ecef.svg?style=for-the-badge&logo=lifecycle&logoColor=black)

## Lenadorsystems:

- ### This project is an assignment from the Lenadorsystems team.

## Demo


https://github.com/almogdadjabir/POS_Lenadorsystems/assets/49059742/31a81392-1fd4-45d2-9f23-8b26e93a6a5f



## Task Requirements

- Add product (Saved in DB)

  > Name,
  > Price...

- Add product to cart

  > add, remove, apply discount, increase or decrease the QTY
- Orders Screen
  > Ability to view past orders
- Setting Screen
  > Ability to toggle tax inclusive or exclusive.

<br>
<hr>
<be>

## Database Schema

#### Product Table

| #     | title        | Type   | Note   |
| ----- | ------------ | ------ | ------ |
| 1     | id           | long   | PK     |
| 2     | name         | String |        |
| 3     | price        | double |        |

#### Invoice Table

| #     | title        | Type   | Note   |
| ----- | ------------ | ------ | ------ |
| 1     | id           | long   | PK     |
| 2     | total        | double |        |
| 3     | totalDiscount| double |        |

#### Order Table

| #     | title        | Type   | Note   |
| ----- | ------------ | ------ | ------ |
| 1     | id           | long   | PK     |
| 2     | productId    | long   | FK     |
| 3     | invoiceID    | long   |  FK    |
| 4     | quantity     | int    |        |
| 5     | discount     | double |        |
| 6     | total        | double |        |

<br>
<hr>
<be>

## Screens
- SplashScreen
- MainActivity Screen
    1. Add To Cart
    2. Manage the cart </br>
       a. Increase the qty. </br>
       b. Decrease the qty. </br>
       c. Add discount (per item). </br>
       d. Remove from cart. </br>
    3. Calculate</br>
       a. Total.</br>
       b. Tax.</br>
       c. SubTotal.</br>
       d. Total QTY.</br>
       d. Total Discount.</br>
- AddProduct Screen</br>
  a. Add New Product.</br>
  NOTE: In this feature, I have implemented both practices:</br>
  a. Adding a product using a custom dialog.</br>
  b. Adding a product through an activity triggered by an intent that waits for a result, and I have also implemented the intent extras.
  </br>
- Report Screen</br>
  a. see all previous sales reports.

<br>
<hr>
<be>

## Architecture and Design Pattern:

I have employed the MVVM architecture, utilizing Room for data storage, DAO for database configuration, Repository for managing data at the data layer, and ViewModel to encapsulate UI-related logic and facilitate data transfer.

<br>
<hr>
<be>

## Integration with Epson Printer SDK:
<br>

Unfortunately, I do not have access to the printer required for testing the integration part. Consequently, I have not been able to implement the integration. However, I have created a class named "PrintMaster" that functions as a master class, designed to encompass various types of printers that our POS system should be compatible with. Furthermore, I have developed a function that I execute after payment to simulate the printing functionality.

<br>

```java
printInvoice(
    orders, // orders list
    invoiceId, // invoice Id
    totalTextView.getText().toString(), // Total
    subTotalTextView.getText().toString(), // Sub Total
    qtyTextView.getText().toString(), // Total Quantities
    discountTextView.getText().toString(), // Total Discounts
    taxTextView.getText().toString() // Tax on Total
);
```

<br>
<hr>
<be>

## Tax Calculation:
<br>

I've added a switch to handle tax calculations, allowing both inclusive and exclusive methods. The switch defaults to the inclusive mode. When users toggle it, the calculation method changes accordingly, updating all the calculations based on the chosen tax type.


<br>

```java
public static double taxCalculator (double amount, boolean isExclusive){
    if(isExclusive) {
        return (amount * (1 + taxRate / 100)) - amount;
    }else{
        return amount - (amount / (1 + taxRate / 100));
    }
}
```
