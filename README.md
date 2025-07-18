# 앱 개발 가이드

---

## 1. Splash 이미지 및 아이콘

### 1-1. 이미지 사이즈
- 258x258
- 172x172
- 200x80

### 1-2. 아이콘 생성
- 사용 사이트: [icon.kitchen](https://icon.kitchen/i/H4sIAAAAAAAAA1VQu24CMRD8l017xSVKmmspUqWCLoqivfPaWPhuke1LQIiOjpqWComPg49g7eMhXNje8Xhmd1bwh66nANUKFPrZZEotQaXRBSpAm5Gzc%2FQxPQeSA7iPznakoADbcCdIy6x%2Ba1SwLqA2I3bsBX3ReQlNm8lyLpoQaRGHOl0qOO82p%2BP%2BtD0IWN9ITf6fgE%2BPylKXvTP6OshiWZZCyNDbkxN2xonG%2B0fuZDzFQdH6RuA8zaO5q4o24wZlIJOIYkbJW5svDLN7CjFloq6lSLesepci%2BxZH5dnmMDjI%2Fk81%2FKwvPydEqVUBAAA%3D)

---

## 2. Google Firebase 프로젝트 추가

---

## 3. Google AdMob 생성 및 추가

---

## 4. KeySigning Example

```bash
keytool -genkeypair \
  -alias apx101_alias \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -keystore apx101_keystore.jks \
  -storepass apx0119 \
  -keypass apx0119 \
  -dname "CN=APX, OU=Dev, O=APX Inc, L=Seoul, ST=Seoul, C=KR"
