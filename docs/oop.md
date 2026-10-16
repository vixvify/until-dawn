# การสาธิต 4 เสาหลักของ OOP

ขอบเขตนี้มีเพียงตัวตนของผีสามชนิดและข้อมูลเริ่มต้นของเกม ยังไม่มีการเคลื่อนที่ การโจมตี หรือ AI ผี ชื่อและคำบรรยายเป็นตัวอย่างที่แก้ภายหลังได้

## 1. Encapsulation — ห่อหุ้มข้อมูล

`entity/Ghost.java` เก็บ `name` เป็น `private final` ตรวจชื่อใน constructor และเปิดให้อ่านผ่าน `getName()` จึงสร้างผีชื่อว่างหรือเปลี่ยนชื่อจากภายนอกไม่ได้

`core/GameSession.java` เก็บ `night` และ `ghosts` เป็น `private final` ใช้ `List.copyOf()` เพื่อคัดลอกรายชื่อและไม่เปิดให้เพิ่มหรือลบผีผ่าน getter อีกทั้งตรวจจำนวนผีให้ตรงกับข้อมูลคืน การป้องกันนี้ครอบคลุมโครงสร้างรายการ หากเพิ่มสถานะที่เปลี่ยนได้ในผีภายหลัง ต้องห่อหุ้มสถานะนั้นภายใน `Ghost` ด้วย

## 2. Inheritance — สืบทอด

`WatcherGhost`, `WandererGhost` และ `ListenerGhost` ใช้ `extends Ghost` จึงใช้การเก็บชื่อ การตรวจชื่อ และ `getName()` จากคลาสแม่ร่วมกัน แต่ละ constructor เรียก `super(...)` เพื่อส่งชื่อให้คลาสแม่

## 3. Abstraction — กำหนดสัญญาร่วม

`Ghost` เป็น `abstract class` จึงสร้างด้วย `new Ghost(...)` ตรง ๆ ไม่ได้ เมธอด `describePresence()` เป็น abstract เพื่อกำหนดว่าผีทุกชนิดต้องบอกลักษณะการปรากฏตัวของตัวเอง ผู้เรียกไม่ต้องรู้วิธีสร้างคำบรรยายของแต่ละชนิด

## 4. Polymorphism — เรียกแบบเดียว ได้พฤติกรรมตามชนิดจริง

ทั้งสามคลาสใช้ `@Override` กับ `describePresence()` และคืนคำบรรยายต่างกัน `GameSession` เก็บทั้งหมดใน `List<Ghost>` แล้วเรียก:

```java
return ghosts.stream().map(Ghost::describePresence).toList();
```

แนวคิดเดียวกับการเขียนลูป:

```java
for (Ghost ghost : session.getGhosts()) {
    System.out.println(ghost.describePresence());
}
```

Java เลือกเมธอดตามชนิดจริงของวัตถุขณะทำงาน จึงไม่ต้องใช้ `instanceof` หรือ `switch` แยกชนิดผี และเพิ่มชนิดใหม่ได้โดยไม่แก้เมธอดรวมคำบรรยาย

## จุดที่ใช้จริง

1. กด `NEW GAME` แล้ว `SceneManager.startNewGame()` เรียก `GameSession.createSingleNight()`
2. Factory สร้างผีใหม่ทั้งสามตัวสำหรับคืนที่ 1 เวลา 12 AM–6 AM
3. `NightController` อ่านจำนวนผีและเรียก `session.describePresences()` ผ่านสัญญาร่วม
4. เลื่อนเมาส์ค้างเหนือข้อความ `ON DUTY UNTIL ... / 3 PRESENCES` เพื่อดู tooltip คำบรรยายผี

## หลักฐานจาก tests

- `GhostTest`: การตรวจชื่อใช้ร่วมกันผ่าน constructor ของคลาสแม่
- `GameSessionTest`: จำนวนและชนิดผีถูกต้อง, แต่ละเกมได้วัตถุใหม่, แก้รายการจากภายนอกไม่ได้ และปฏิเสธข้อมูลที่ไม่ตรงกัน
- `dispatchesToAnAdditionalSubtypeWithoutChangingSessionCode`: สร้างชนิดผีเฉพาะใน test แล้วตรวจว่า `GameSession` เรียก implementation ใหม่นั้นได้ โดยไม่แก้โค้ด session ชนิดทดสอบนี้ไม่ถูกเพิ่มในเกมจริง

รันด้วย `mvn clean verify` โดยไม่ต้องเปิด JavaFX ส่วน CI ใช้คำสั่งเดียวกันบน Java 21
