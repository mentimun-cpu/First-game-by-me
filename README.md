# 🎮 Fantasy Gacha Game (update dari tugas besar PBO semester 3)

## 📋 Deskripsi Proyek
**Fantasy Gacha Game** adalah aplikasi game gacha sederhana berbasis GUI yang dibangun dengan JavaFX. Game ini mengimplementasikan konsep Pemrograman Berorientasi Objek (PBO) sebagai landasan dasar untung mengembangkan game.

## ✨ Fitur Utama
- **🎰 Sistem Gacha**: Single pull (100 Gold) dan Multi pull 10x (900 Gold)
- **📦 Inventory Management**: CRUD lengkap untuk item
- **👤 Player Management**: Statistik dan progres player
- **💰 Economy System**: Jual/beli item dengan sistem harga

## 🎯 Konsep PBO yang Diimplementasikan

### ✅ **Class & Object**
- `GameItem` (Abstract Class)
- `Character` dan `Equipment` (Inheritance)
- `Player`, `GachaService`, `GachaController`, dll.
- **Total: 8+ class non-GUI**

### ✅ **Constructor & Method**
- Setiap class memiliki constructor minimal 1
- Setiap class memiliki method minimal 3
- Contoh: `Character` memiliki `displayInfo()`, `levelUp()`, `getPower()`

### ✅ **Encapsulation**
- Semua atribut menggunakan `private`
- Akses menggunakan getter dan setter
- Contoh: `player.getMoney()`, `item.setName()`

### ✅ **Inheritance**
- `GameItem` → `Character`
- `GameItem` → `Equipment`
- Implementasi properti dan method warisan

### ✅ **Polymorphism**
- **Overriding**: `displayInfo()` di class child
- **Overloading**: `levelUp()` dan `levelUp(int amount)`

### ✅ **Abstract Class**
- `GameItem` sebagai abstract class
- Abstract method: `displayInfo()`

### ✅ **Interface**
- `IGachaItem` dengan methods: `getDropRate()`, `onObtain()`, `canBeTraded()`

### ✅ **Scanner**
- Digunakan untuk input konfigurasi awal
- Implementasi di `MainApp.java`

## 🏗️ Struktur Proyek

```
fantasy-gacha-game/
├── src/main/java/com/tugasbesar/
│   ├── MainApp.java                          # Entry point aplikasi
│   ├── controller/
│   │   ├── GachaController.java              # Controller untuk gacha
│   │   └── InventoryController.java          # Controller untuk inventory
│   ├── model/
│   │   ├── abstracts/
│   │   │   └── GameItem.java                 # Abstract class
│   │   ├── interfaces/
│   │   │   └── IGachaItem.java               # Interface
│   │   ├── enums/
│   │   │   └── Rarity.java                   # Enum untuk rarity
│   │   ├── Character.java                    # Model karakter
│   │   ├── Equipment.java                    # Model equipment
│   │   ├── GachaResult.java                  # Model hasil gacha
│   │   ├── Inventory.java                    # Model inventory
│   │   └── Player.java                       # Model player
│   ├── service/
│   │   ├── DatabaseService.java              # Service database (opsional)
│   │   ├── GachaService.java                 # Service untuk gacha
│   │   └── InventoryService.java             # Service untuk inventory
│   └── view/
│       ├── components/
│       │   ├── InventoryTableView.java       # Custom table view
│       │   └── ScannerInputDialog.java       # Dialog input sederhana
│       ├── MainView.java                     # View utama
│       ├── GachaView.java                    # View untuk gacha
│       ├── InventoryView.java                # View untuk inventory
│       └── PlayerView.java                   # View untuk player
├── src/main/resources/com/tugasbesar/
│   └── style.css                             # Styling CSS
└── pom.xml                                   # Konfigurasi Maven
```

## 🎲 Sistem Gacha

### 📊 Drop Rates
| Rarity     | Rate  | Symbol | Base Price |
|------------|-------|--------|------------|
| COMMON     | 60%   | ⚪     | $50        |
| RARE       | 25%   | 🔵     | $200       |
| EPIC       | 10%   | 🟣     | $800       |
| LEGENDARY  | 5%    | 🟡     | $3000      |

### 🎪 Fitur Gacha
- **Single Pull**: 100 Gold per pull
- **Multi Pull 10x**: 900 Gold (hemat 10%)
- **Guarantee System**: Multi pull menjamin minimal 1 item RARE+
- **Animasi**: Feedback visual untuk hasil gacha

## 📦 Sistem Inventory (CRUD)

### ✅ **Create**
- Tambah item dari hasil gacha
- Buat item custom via dialog
- Validasi input

### ✅ **Read**
- Tampilkan semua item dalam TableView
- Filter berdasarkan rarity
- Search by name
- Sort by column

### ✅ **Update**
- Edit informasi item
- Update statistik
- Validasi perubahan

### ✅ **Delete**
- Hapus item dari inventory
- Konfirmasi sebelum hapus
- Sell item untuk Gold

## 🖥️ GUI Features

### 🎨 Tampilan
- **Modern UI** dengan gradient colors
- **Responsive design** untuk berbagai ukuran
- **Color coding** berdasarkan rarity
- **Visual feedback** untuk interaksi

### 📋 Komponen GUI
1. **Tab System**: Navigasi antara Gacha, Inventory, dan Player
2. **TableView**: Menampilkan data dengan kolom yang dapat diurutkan
3. **Dialog Box**: Input dan konfirmasi
4. **Progress Bars**: Visualisasi statistik
5. **Buttons**: Dengan styling dan hover effects

### 🎮 Controls
- **Mouse Interaction**: Click, hover, drag
- **Keyboard Support**: Navigation dengan keyboard
- **Input Validation**: Validasi field input

## 🚀 Cara Menjalankan

### Prerequisites
- **Java JDK 17** atau lebih tinggi
- **JavaFX SDK 17** atau lebih tinggi
- **Maven** (opsional)

### Cara 1: Run dengan IDE (Recommended)
1. Clone/download proyek
2. Buka di IDE (IntelliJ IDEA, Eclipse, NetBeans)
3. Pastikan JavaFX dikonfigurasi
4. Run `MainApp.java`

### Cara 2: Run dengan Maven
```bash
# Clone repository
git clone [repository-url]

# Navigasi ke folder proyek
cd fantasy-gacha-game

# Compile dan run
mvn clean javafx:run
```

### Cara 3: Run dengan Command Line
```bash
# Compile
javac --module-path [path-to-javafx] --add-modules javafx.controls,javafx.fxml -d bin src/**/*.java

# Run
java --module-path [path-to-javafx] --add-modules javafx.controls,javafx.fxml -cp bin com.tugasbesar.MainApp
```

## 🎯 Cara Bermain

### Step 1: Setup Awal
1. Jalankan aplikasi
2. Input nama player
4. Mulai MENGGACHAA!!!! >:)

## 💡 Tips & Strategi

### 💰 Manajemen Gold
1. **Awal Game**: Fokus ke multi pull untuk guarantee rare+
2. **Mid Game**: Balance antara gacha dan upgrade
3. **Late Game**: Koleksi legendary items

### 🎯 Strategi Gacha
1. **Single Pull**: Untuk daily quest atau coba luck
2. **Multi Pull**: Untuk efisiensi dan guarantee
3. **Pity System**: Multi pull selalu beri minimal rare

### 📈 Progress Optimization
1. **Sell Strategy**: Jual item common, keep rare+
2. **Collection Bonus**: Koleksi set rarity untuk bonus

## 👥 Progres
### Development Timeline
1. **Week 1**: Requirement analysis dan design
2. **Week 2**: Core implementation
3. **Week 3**: GUI development
4. **Week 4**: Testing dan refinement

## 📚 Referensi

### Academic References
1. **PBO Concepts**: Inheritance, Polymorphism, Encapsulation
2. **Design Patterns**: MVC, Service Layer, Repository
3. **GUI Design**: JavaFX best practices

### Technical References
1. **JavaFX Documentation**: Official JavaFX docs
2. **Maven Guide**: Build automation
3. **CSS Styling**: JavaFX CSS reference

### Attribution
- **Course**: Pemrograman Berorientasi Objek
- **University**:  Universitas Pancasila
- **Academic Year**: 2025

### Special Thanks
- **Open Source Community**: Libraries dan resources

### Inspiration
- **Gacha Games**: Genshin Impact, Fate/Grand Order
- **Inventory Systems**: RPG games
- **UI Design**: Modern web applications

---

**🎮 Happy Gaming!** Semoga game ini membantu memahami konsep PBO dengan cara yang menyenangkan!
