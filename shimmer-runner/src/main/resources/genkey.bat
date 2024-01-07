@REM 第二种方式生成key
keytool -genkeypair -keyalg RSA -storetype PKCS12 -keysize 2048 -validity 3650 -alias "https-ssl-key" -keystore "https-ssl-key.p12" -storepass "123456" -keypass "123456" -dname "CN=CHEER, OU=CHEER, O=CHEER, L=SZ, ST=SZ, C=CN"
@REM server.ssl.key-store=classpath:springboot.p12
@REM server.ssl.key-store-password=vo1995
@REM server.ssl.key-store-type=pkcs12
@REM server.ssl.key-alias=vonh
@REM server.ssl.enabled=true
@REM server.ssl.protocol=TLS
@REM server.ssl.enabled-protocols=TLSv1.1



keytool -genkeypair -keyalg RSA -storetype PKCS12 -keysize 2048 -validity 3650 -alias "privateKey" -keystore "pri.p12" -storepass "123456" -dname "CN={3}, OU={4}, O={5}, L=SZ, ST=SZ, C=CN"
keytool -exportcert -alias "privateKey" -keystore "pri.p12" -storepass "123456" -file "cert.cer"
keytool -list -rfc -keystore pri.p12 -storepass abc
keytool -import -alias "publicKey" -file "cert.cer" -keystore "pub.p12" -storepass "123456" -noprompt


