# Twitter API

Spring Boot ve PostgreSQL kullanılarak geliştirilmiş, Twitter'ın temel özelliklerini sunan REST API projesi.

Workintech Full Stack Developer eğitimi kapsamında geliştirilmiştir.

## Teknolojiler

- Java 17
- Spring Boot 4.1.2
- Spring Security (HTTP Basic Authentication)
- Spring Data JPA / Hibernate
- PostgreSQL
- Jakarta Validation
- JUnit 5 ve Mockito
- Maven

## Proje Mimarisi

Proje katmanlı mimari kullanılarak geliştirilmiştir.

- **Controller:** HTTP isteklerini karşılar.
- **DTO:** İstek verilerini taşır ve doğrular.
- **Service:** İş kurallarını ve yetki kontrollerini uygular.
- **Repository:** Veritabanı işlemlerini yönetir.
- **Entity:** Veritabanı tablolarını temsil eder.
- **Exception:** Hataları merkezi olarak yönetir.
- **Config:** Spring Security yapılandırmasını içerir.

## Veritabanı

Proje beş tablo içerir:

- `users`
- `tweets`
- `comments`
- `likes`
- `retweets`

Tweet, Comment, Like ve Retweet kayıtları kullanıcılarla ilişkilidir. Comment, Like ve Retweet kayıtları ayrıca Tweet tablosuna bağlıdır.

Veritabanı yapısı `schema.sql` dosyasında bulunmaktadır.

## API Endpoint'leri

| Method | Endpoint | Açıklama |
|---|---|---|
| POST | `/register` | Kullanıcı kaydı |
| POST | `/login` | Kullanıcı girişi |
| POST | `/tweet` | Tweet oluşturma |
| GET | `/tweet/findByUserId/{userId}` | Kullanıcının Tweet'lerini listeleme |
| GET | `/tweet/findById/{id}` | Tweet detayını getirme |
| PUT | `/tweet/{id}` | Tweet güncelleme |
| DELETE | `/tweet/{id}` | Tweet silme |
| POST | `/comment` | Yorum oluşturma |
| PUT | `/comment/{id}` | Yorum güncelleme |
| DELETE | `/comment/{id}` | Yorum silme |
| POST | `/like` | Tweet beğenme |
| POST | `/dislike` | Beğeniyi kaldırma |
| POST | `/retweet` | Retweet oluşturma |
| DELETE | `/retweet/{id}` | Retweet silme |

## Security

Spring Security ve HTTP Basic Authentication kullanılmaktadır.

- Kullanıcı şifreleri BCrypt ile hash'lenir.
- Kayıt ve giriş endpoint'lerine kimlik doğrulaması olmadan erişilebilir.
- Diğer endpoint'lerde kimlik doğrulaması gereklidir.
- Tweet güncelleme ve silme işlemleri yalnızca Tweet sahibi tarafından yapılabilir.
- Yorum silme işlemi yorum veya Tweet sahibi tarafından yapılabilir.
- Aynı kullanıcı aynı Tweet'i birden fazla kez beğenemez veya retweet edemez.

## Kurulum

1. PostgreSQL üzerinde `twitter_db` adında bir veritabanı oluşturun.
2. `schema.sql` dosyasını PostgreSQL `psql` aracıyla veritabanına aktarın:

   ```bash
   psql -U postgres -d twitter_db -f schema.sql
   ```

3. PostgreSQL şifrenizi `DB_PASSWORD` ortam değişkeni olarak tanımlayın.
4. Maven testlerini çalıştırın:

   ```bash
   ./mvnw test
   ```

   Windows üzerinde `mvnw.cmd test` kullanabilirsiniz.

5. Spring Boot uygulamasını çalıştırın.

Backend varsayılan olarak `http://localhost:3000` adresinde çalışır.

## Unit Tests

JUnit 5 ve Mockito kullanılarak 14 farklı Service metodu için Unit Test yazılmıştır.

Test senaryoları arasında kullanıcı kaydı, Tweet oluşturma, güncelleme ve silme, yorum işlemleri, beğeni işlemleri, Retweet işlemleri ve başarısız giriş kontrolü bulunmaktadır.

**Son doğrulanan Maven sonucu:**

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

14 Unit Test ve bir Spring Boot uygulama başlatma testi başarıyla çalışmıştır.

## React ve CORS

React arayüzü ve CORS entegrasyonu projenin sonraki aşamasında tamamlanacaktır.