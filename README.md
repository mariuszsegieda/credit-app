# credit-app
cd D:\INTECA2\database
docker build -t mariuszsegieda/database .
cd D:\INTECA2\credit
docker build -t mariuszsegieda/credit_app .
cd D:\INTECA2\customer
docker build -t mariuszsegieda/customer_app .
cd D:\INTECA2\product
docker build -t mariuszsegieda/product_app .
cd D:\INTECA2
docker-compose up