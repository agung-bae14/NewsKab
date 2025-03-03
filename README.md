# README

## **Konfigurasi IP Address**

Proyek ini memerlukan perubahan IP address agar aplikasi dapat terhubung ke backend yang berjalan di laptop lokal. Silakan ikuti langkah-langkah berikut untuk mengubah IP di dalam file yang diperlukan:

### **1. Temukan IP Laptop Anda**

Sebelum melakukan perubahan, pastikan Anda mengetahui IP laptop Anda di jaringan lokal.

- **Windows**: Jalankan perintah berikut di Command Prompt:

  ```sh
  ipconfig
  ```

  Cari bagian `Wireless LAN adapter Wi-Fi` atau `Ethernet adapter` lalu catat `IPv4 Address`.

- **Mac/Linux**: Jalankan perintah berikut di Terminal:

  ```sh
  ifconfig | grep "inet "
  ```

  atau

  ```sh
  ip a
  ```

  Catat IP yang sesuai dengan jaringan lokal Anda.

### **2. Ubah IP di File Berikut**

Setelah mendapatkan IP laptop, buka dan edit file berikut:

#### **a. **``

Ganti **192.168.1.9** dengan IP laptop Anda.

```kotlin
const val BASE_URL = "http://192.168.1.9:8000/"
```

#### **b. **``

Cari bagian yang mengakses API dan ubah IP-nya.

```kotlin
private val baseUrl = "http://192.168.1.9:8000/api/news"
```

#### **c. **``

Pastikan konfigurasi retrofit menggunakan IP laptop Anda.

```kotlin
private const val BASE_URL = "http://192.168.1.9:8000/"
```

### **3. Simpan dan Jalankan Aplikasi**

Setelah melakukan perubahan, simpan semua file dan jalankan ulang aplikasi.

Jika ada masalah, pastikan backend berjalan di laptop Anda dan firewall tidak memblokir koneksi.

---

Jika masih mengalami kendala, silakan periksa koneksi jaringan atau restart backend Anda. 🚀

