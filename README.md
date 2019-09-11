# o2o
- 1 实体类 不能用基本类型  当为null时 会自动赋值0 有的数据不适合值为0   解决方法用引用类型
- 2 
 ```xml
    <plugin>
               <artifactId>maven-compiler-plugin</artifactId>
               <version>3.8.0</version>
               <configuration>
                 <source>1.8</source>
                 <target>1.8</target>
                 <encoding>UTF-8</encoding>
               </configuration>
             </plugin>
  ```
   
 - 3 RuntimeException 可以让事务回滚 终止事务 普通的 exception不能
 - 4 基本类型的传值 不改变其本身  引用类型改变其本身