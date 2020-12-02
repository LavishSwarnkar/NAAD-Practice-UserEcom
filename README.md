[>>> Download User APK here <<<](https://github.com/lswarnkar1/NAAD-Practice-UserEcom/raw/v0.8/app/build/outputs/apk/debug/app-debug.apk)

# Ecom User v0.8 Changelog

- FCM Sender

# Task

- Create order class (in both apps)
  
- Create new class OrderStatus in Order class as
  
  ```java
  public static class OrderStatus {
  
         public static final int PLACED = 1 // Initially (U)
         , DELIVERED = 0, DECLINED = -1;     //(A)
  
  }
  ```
  
- Send order object when user checkouts from CartActivity (U)
  
- Notify Admin using FCM (Subscribe Admin to topic "admin") (U)
  
- Receive the order object in FCMReceiver of (A)
  
- Create layout for orderItem with following details : (A)
  
  - OrderId, OrderTime
    
  - UserDetails
    
  - OrderItems (ListView / Dynamically adding items using addView())
    
  - Actions :
    
    - PLACED : Show DELIVERED, DECLINE buttons
      
    - DELIVERED, DECLINED : No actions
      
- Create RecyclerView for orderItems (A)
  
- OnClickListeners for DELIVERED & DECLINE buttons (A) :
  
  - Update the status
    
  - Notify User using FCM
    
  - Subscribe User to topic "users"
    
  - Use data parameter of JSON to notify users
    
  - Add for parameter with userEmail in data of payload
    
- Receive orderUpdates notification in FCMReceiver (U)
  
  Check if the order is of current User and notify accordingly