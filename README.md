# Module-2
## Link-Deployment
- https://selective-carlie-evanhwzorgs-568d8cef.koyeb.app/
## List The Code Quality Issue
- **Empty Method / Missing Nested Comment**
	- **The issue**: terdapat method **setUp()** yang tidak memiliki kode / instruksi pada ProductRepositoryTest.java.
	- **Kenapa:** empty method tidak disarankan oleh sonar karena menimbulkan ambiguitas pada developer lain karena akan muncul asumsi jika kode tersebut antara belum diselesaikan dan harus kosong.
	-**Cara memperbaiki**: Saya menghapus fungsi tersebut karena tidak digunakan sehingga hal ini menghilangkan ambiguitas.
- **Field injection issue**
	- **The issue:** terdapat injection method dengan cara field injection pada ProductController.java (menggunakan @AutoWired langsung pada deklarasi variable untuk class ProductService)
	- **Kenapa:** field injection tidak disarankan oleh sonar karena rawan terjadi NullPointerException ketika terdapat developer atau engineer yang melakukan pemanggilan **new ProductController()**, tetapi tidak memasukkan dependensi terhadap class ProductService
	- **Cara memperbaiki:** mengubah field injection menjadi constructor injection, yakni dengan menghapus anotasi @Autowired, menambahkan sifat final pada variabel ProductService (access-modifiers: private), dan menambahkan anotasi @RequiredArgsConstructor dari Lombok di atas kelas ProductContoller agar constructor dibuat secara otomatis.
- **Unnecessary public modifier in test class**
	- **The issue:** terdapat public modifier pada deklarasi class HomePageControllerTest, ProductControllerTest, dan ProductServiceImplTest.
	- **Kenapa:** Junit 5 sudah tidak membutuhkan adanya tambahan public modifier pada deklarasi class sehingga menghapusnya dapat membuat kode lebih bersih
	- **Cara memperbaiki:** menghilangkan public modifier pada deklarasi classes tersebut.
- **Privileges of Scorecards**
	- **The issue:** terdapat permission "read-all" pada workflow scorecard
	- **Kenapa:** meningkatkan kerentanan / ancaman ketika workflow disusupi dan permission adalah 'read-all' yang tidak hanya membaca kode saja, tetapi keseluruhan aktivitas pada github activites (pull-request, discussions, etc)
	- **Cara memperbaiki:** mengganti 'read-all' menjadi 'read' saja sehingga workflow hanya dapat membaca kode saja.
## Ulasan terkait CI/CD workflows atau Pipeline yang saya buat
Workflows yang saya inisiasi atau bentuk sudah memenuhi definisi Continuous Integration dan Continuous Deployment. Hal tersebut dikarenakan pada Continuous Integration (CI), setiap perubahan kode yang di-push atau di-pull-request ke branch main secara otomatis akan memicu proses build, uji unit-test, dan analisis kualitas kode menggunakan SonarCloud. Ketiga proses ini dilakukan agar perubahan yang ada dapat tervalidasi dan teruji dengan baik sebelum digabungkan ke branch main sehingga potensi bug dapat terdeteksi lebih awal. 

Setelah proses Continuous Integration sudah dilakukan dan semua komponen berhasil, maka dilanjutkan ke proses Continuous Deployment, yakni proses deployment secara otomatis ke lingkungan produksi / production. Penerapan Continuos Deployment ini tidak serta-merta langsung melakukan deploy. Melainkan, ada ketergantungan dengan hasil pada Continuous Integration yang didefinisikan pada bagian "workflow_run" sehingga ketika pada proses Continuous Integration masih gagal, proses deployment tidak akan berlangsung. Selain itu, proses deployment ini juga sudah terintegrasi dengan PaaS koyeb menggunakan koyeb CLI yang otomatis melakukan redeploy pada proses Continuous Deployment ini.

# Module-1
## Refleksi - 1
Setelah melakukan proses implementasi berbagai fitur, seperti Create, Read, Update, dan Delete pada program ini, saya sudah berusaha mencoba menerapkan beberapa prinsip clean code. Hal tersebut diantaranya adalah
  - Meaningful names: 
    - Pada kode di folder repository, saya telah menulis dengan jelas terkait fungsi yang merepresentasikan proses edit dan delete dengan nama "update" dan "delete". Lalu, saya juga menambahkan dua fungsi lain, yakni "findById" untuk mencari produk berdasarkan id product serta "findIndexOf" untuk mencari suatu produk dengan id tertentu terletak di index ke berapa pada tempat penyimpanannya.
- Meaningful comments:
   - Saya sudah tidak lagi melakukan proses dokumentasi komen setiap line, tetapi saya sudah mencoba membuat nama fungsi terkait relevan dengan tugasnya. Kemudian, penambahan comment yang saya lakukan terdapat pada folder repository juga yang memberi informasi bahwa saya membuat method helper pada class ProductRepository. Hal itu, saya lakukan agar tidak membuat kebingungan terhadap developer lain yang akan membaca kode saya karena method tersebut memiliki visibility private tersendiri dan tidak digunakan di luar class.

- Penerapan single responsibility principle: 
   - Kode program yang dibentuk pun telah menerapkan prinsip ini, yakni memisahkan business logic pada folder Service dan manipulasi data pada folder Repository. Salah satu contohnya adalah pada method create di file "ProductRepository.java" tidak ada pengecekan UUID terlebih dahulu sebelum add data ke dalam ArrayList, tetapi pada file "ProductServiceImpl.java" akan dicek terlebih dahulu adanya UUID atau tidak dan menjalankan proses manipulasi data dengan method yang terdapat pada repository.

Selain itu, dalam hal Secure Coding, saya sudah menerapkan proses pembuatan ID menggunakan UUID sehingga hal ini jauh lebih aman ketika terdapat proses manipulasi data yang dilakukan di client (contohnya ketika proses editing yang melibatkan route-path dengan adanya id).

Namun, saya menyadari juga bahwa program saya belum sempurna. Kelemahan program saya, yakni belum adanya input validation dan input sanitation ketika adanya proses manipulasi data melalui client. Maka dari itu, hal yang mungkin terjadi adalah ketidaksesuaian tipe data ketika pada proses client terdapat manipulasi html atau kesalahan input (implikasi dari tidak adanya input validation). Kemudian, ada juga kemungkinan XSS injection yang dapat memanipulasi web yang berjalan di production karena tidak adanya sanitasi input dari user/client. Kemudian, saya juga belum menerapkan error handling sehingga jika terdapat masalah pada program, web dapat langsung menampilkan raw errornya dan hal itu jelas melakukan exposure terhadap program saya.

Maka dari itu, program ini akan memiliki keamanan yang jauh lebih baik jika adanya penambahan input validation, input sanitation, dan error handling yang baik dan informatif terhadap developer program terkait.

## Refleksi - 2
### Refleksi - 2.1
Menuliskan unit-test adalah salah satu pekerjaan yang membutuhkan tenaga lebih karena saya harus membuat beberapa skenario yang mungkin terjadi pada fungsi yang saya buat. Terlebih jika terdapat berbagai conditional yang diterapkan. Namun, ketika melihat "all tests passed", ada kebahagiaan tersendiri karena program terlihat berjalan mulus.

Lalu, menurut saya sendiri, tidak ada exact numbers terkait banyaknya unit tests yang harus dibuat karena proses testing ini bergantung pada kompleksitas logika programnya. Akan tetapi, hal yang dapat menjadi acuan bahwa unit tests yang kita buat dapat memverifikasi program yang diuji adalah dengan melakukan proses pembuatan skenario positif dan negatif. 

Skenario positif adalah skenario di mana program berjalan sukses sesuai keinginan developer. Contohnya, seperti proses pembuatan produk yang berhasil terbuat dan tersimpan di dalam ArrayLists. Skenario negatif adalah skenario ketika program gagal mencapai tujuan utama dari fungsi yang dibuat. Sebagai contoh, kita melakukan uji pembuatan produk kembali, tetapi di-akhir, kita pastikan bahwa produk tidak berhasil dibuat dengan berbagai kondisi yang membuat proses itu gagal. Maka, dengan adanya kedua skenario tersebut, dapat dipastikan bahwa kita sudah memastikan suatu fungsi berjalan sebagaimana mestinya.

Selain itu, code coverage pun sering menjadi tolak ukur untuk memastikan bahwa fungsi yang kita uji benar-benar sudah ter-cover secara keseluruhan. Akan tetapi, code coverage tidak sama dengan memastikan / menjamin program tidak ada bug. Hal tersebut  dikarenakan code coverage hanya melakukan verifikasi bahwa baris kode pada suatu fungsi sudah pernah dijalankan (tidak melihat business logic dari fungsi itu). Oleh sebab itu, masih terdapat kemungkinan bahwa suatu program memiliki bug, seperti kesalahan merepresentasikan fungsi dengan isi utamanya. Contohnya, fungsi pertambahan, tetapi isinya pengurangan dan dalam unit-testnya tetap dibuat sebagaimana isi fungsinya. 

### Refleksi 2.2
Setelah membuat functional testing pada CreateProduct, saya mendapatkan issue terbaru. Hal itu adalah ketika  terdapat requirements pembuatan functional testing yang melakukan verifikasi pada banyak item di product list (dengan setup procedures dan instance variables yang sama). Saya menyadari bahwa akan ada redundant code ketika proses functional testing ini berdiri independen (tanpa adanya inheritance) karena semua setup dan variables tetap sama.

Maka dari itu, menurut saya, akan lebih baik jika program menerapkan proses inheritance. Hal tersebut dapat dilakukan dengan membuat class pondasi, seperti "BaseFunctionalTest" yang berisi setup procedures dan instance variables. Kemudian, setiap functional test, seperti CreateProduct atau semacamnya yang berkaitan dengan functional test dapat membuat class terpisah dan mewarisi setup serta instance variables tersebut dengan proses "extends class" dari BaseFunctionalTest class sehingga file functional test yang digunakan untuk proses pengujian cukup menuliskan fungsi testingnya tanpa melakukan setup kembali / dari awal. Melalui metode ini, dapat dipastikan bahwa kode tidak akan redundant kembali.
