# BỘ TEST CASE TỔNG HỢP – NGHIỆM THU UAT AI CHATBOT VINFAST
> **Phiên bản:** 2.0 – Merged & Enhanced  
> **Ngày tạo:** 25/04/2026  
> **Nguồn gộp:** `Testcase.csv` (team Dev) + `UAT_Customer_Feedback_TestCases` (feedback KH demo) + Bổ sung mới  
> **Tổng cộng: 80 Test Cases | 12 Nhóm**

---

## 📋 RÀ SOÁT FILE TESTCASE.CSV GỐC (EXPERT REVIEW)

> [!NOTE]
> **Kết quả rà soát từ file CSV gốc của Team:**
> - Có **6 nhóm, ~28 test case** đã tạo, đa số PASS
> - **2 case FAILED:** TC1.6 (tra cứu "Xe VF" lấy từ RAG) và TC6.2 (kết hợp 2 CTKM loại trừ)
> - **3 case TODO:** TC5.2 (lỗi API 500), TC5.3 (timeout), TC5.4 (lỗi get-vinclub-info)
> - **Lỗi dữ liệu:** TC6.1 ghi ngược tỷ lệ % Thu Xăng Đổi Điện (xe máy ghi 3%, ô tô ghi 5% → **ĐÚNG phải là xe máy 5%, ô tô 3%**)

> [!WARNING]
> **Các GAP chưa bao phủ trong file CSV gốc:**
> 1. Không có test case cho Chính sách O2O (feedback #2 từ chị Chi)
> 2. Không test ưu đãi được phép áp dụng ĐỒNG THỜI (chỉ test loại trừ)
> 3. Không test xe ngừng sản xuất / xe không tồn tại (ngoài VF10)
> 4. Không test phân loại Ô tô vs Xe máy (Mãnh liệt, sạc miễn phí...)
> 5. Không test các yêu cầu UX: Form Lead, Disclaimer AI, Nút tư vấn viên, Lịch sử chat, Bảo mật
> 6. Thiếu test giá xe máy điện chi tiết từ RAG (Amio, Flazz, Zgoo, Vero X...)
> 7. Thiếu test các xe thương mại (EC Van, Minio Green, Limo Green, Herio Green, Ebus)
> 8. Thiếu test chính sách chuyển đổi pin thuê → pin mua
> 9. Thiếu test chính sách Nhân đôi Lộc Tết, Tiên phong VF MPV 7

---

## NHÓM 0: KIỂM TRA UI WIDGET (2 Cases) ← Từ CSV gốc

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| UI-01 | Hiển thị đầy đủ thành phần Widget | "Tính dự toán lăn bánh xe VF7" → Hiển thị Widget | Widget hiển thị đầy đủ: **Lựa chọn cấu hình xe** ∙ **Phiên bản** ∙ **Màu sắc cơ bản** ∙ **Lựa chọn ưu đãi** ∙ **Tỉnh/Thành phố** ∙ **Button dự toán chi phí lăn bánh**. Widget không bị duplicate, không xê lệch | High | PASS |
| UI-02 | Widget không bị duplicate khi gọi lại | Gọi tính chi phí lăn bánh 2 lần liên tiếp cho cùng 1 xe | Chỉ hiển thị 1 Widget mới nhất, Widget cũ bị thay thế hoặc ẩn đi. Không xuất hiện 2 Widget song song | High | — |

---

## NHÓM 1: KÍCH HOẠT & PHẠM VI – TRIGGER & SCOPE (7 Cases) ← Gộp CSV + Mở rộng

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| TC1.1 | Kích hoạt đúng intent chi phí | "mua VF5 hết bao nhiêu" | Bot kích hoạt skill `vinfast-ctkm`, gọi tool phù hợp. Hiển thị: *"Mình sẽ giúp bạn tính chi phí lăn bánh VF 5 nhé!"* → Show Widget | High | PASS |
| TC1.2 | Kích hoạt đúng intent CTKM | "tháng này VinFast ưu đãi gì" | Bot kích hoạt skill, gọi `rag-search`. Hiển thị danh sách CTKM đang hiệu lực: Mãnh liệt, Thu Xăng Đổi Điện, Mua xe 0 Đồng, Voucher GTĐ... | High | PASS |
| TC1.3 | Bỏ qua xe hãng khác | "chi phí lăn bánh Toyota Vios" | Bot KHÔNG kích hoạt skill. Hiển thị: *"Mình chỉ hỗ trợ thông tin về xe VinFast thôi nhé. Bạn muốn tìm hiểu dòng xe VinFast nào không?"* | High | PASS |
| TC1.4 | Bỏ qua xe xăng VinFast (đã ngừng) | "khuyến mãi VinFast Fadil" | Bot KHÔNG kích hoạt. Hiển thị: *"VinFast Fadil là dòng xe xăng đã ngừng sản xuất. Hiện VinFast chỉ kinh doanh xe điện. Bạn muốn xem dòng xe điện nào?"* | High | PASS |
| TC1.5 | Bỏ qua intent ngoài lề | "đặt phòng Vinpearl Nha Trang" | Bot KHÔNG kích hoạt skill CTKM. Điều hướng sang skill booking-hotel hoặc trả lời: *"Mình không hỗ trợ đặt phòng khách sạn. Bạn có thể liên hệ Vinpearl trực tiếp nhé"* | Medium | PASS |
| TC1.6 | Tra cứu "Xe VF" → phải lấy từ RAG | "Xe vf" | Bot gọi `rag-search` để lấy dữ liệu từ RAG. Hiển thị: Danh sách giá và CTKM các dòng xe VF (VF 3, VF 5, VF 6, VF 7, VF 8, VF 9, VF MPV 7). **KHÔNG gọi internet tìm kiếm** | **Critical** | ~~FAILED~~ |
| TC1.7 | Tra cứu "Xe VinFast" → phải lấy từ RAG | "Xe VinFast có những loại nào?" | Bot gọi `rag-search`. Hiển thị phân loại rõ: **Ô tô điện:** VF 3, VF 5, VF 6, VF 7, VF 8, VF 9, VF MPV 7, Minio Green, EC Van, Limo Green, Herio Green, Nerio Green. **Xe máy điện:** Evo, Evo Lite, Evo Grand, Feliz II, Feliz 2025, Viper, Vero X, Amio, Flazz, Zgoo... | **Critical** | — |

---

## NHÓM 2: CHUẨN HÓA DỮ LIỆU – NORMALIZATION (8 Cases) ← Gộp CSV + Mở rộng

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| TC2.1 | Chuẩn hóa VF + Edition | "Chi phí lăn bánh vf 8 PLUS" | Bot chuẩn hóa → model: `VF 8`, edition: `Plus`. Hiển thị Widget VF 8 Plus với giá **1.199.000.000 VNĐ** | High | PASS |
| TC2.2 | Chuẩn hóa khoảng trắng thừa | "chi phí VF 9 TIÊU CHUẨN" | Bot chuẩn hóa → model: `VF 9`, edition: `Tiêu chuẩn`. Hiển thị Widget VF 9 Eco giá **1.499.000.000 VNĐ** | High | PASS |
| TC2.3 | Chuẩn hóa dòng Green | "mua limo green eco" | Bot chuẩn hóa → model: `Limo Green`, edition: `Eco`. Hiển thị Widget Limo Green giá **749.000.000 VNĐ** | High | PASS |
| TC2.4 | Chuẩn hóa EC Van | "giá EC VAN" | Bot chuẩn hóa → model: `EC Van`. Hiển thị giá **285.000.000 VNĐ** (kèm pin) | High | PASS |
| TC2.5 | Chuẩn hóa xe máy điện | "giá xe máy điện vero x" | Bot chuẩn hóa → model: `Vero X`. Hiển thị: *"VinFast Vero X – Giá niêm yết: **34.900.000 VNĐ** (đã bao gồm VAT, 01 pin và 01 bộ sạc)"* | High | PASS |
| TC2.6 | Chuẩn hóa viết hoa/thường | "khuyến mãi FELIZ" | Bot chuẩn hóa → model: `Feliz`. Hiển thị CTKM áp dụng cho Feliz 2025: *Thu Xăng Đổi Điện -5% (nếu đủ ĐK), Sạc miễn phí...* | High | PASS |
| TC2.7 | Chuẩn hóa viết liền "vf8" | "giá vf8" | Bot chuẩn hóa → `VF 8`. Hiển thị Widget VF 8 Eco: **1.019.000.000 VNĐ**, Plus: **1.199.000.000 VNĐ** | High | — |
| TC2.8 | Chuẩn hóa "mpv7" / "mpv 7" | "giá lăn bánh mpv7" | Bot chuẩn hóa → `VF MPV 7`. Hiển thị Widget VF MPV 7 giá **819.000.000 VNĐ** | High | — |

---

## NHÓM 3: LUỒNG XỬ LÝ PHÂN NHÁNH – FLOW & BRANCHING (7 Cases) ← Từ CSV gốc

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| TC3.1 | Nhánh: Chưa rõ loại xe | "tính tiền mua xe VinFast" | Bot gọi `ask-vehicle-type` → Hiển thị widget chọn: **[Ô tô điện]** hoặc **[Xe máy điện]**. Bot: *"Bạn muốn tìm hiểu ô tô điện hay xe máy điện ạ?"* | High | PASS |
| TC3.2 | Nhánh: Chọn Ô tô từ Widget | User click "Ô tô điện" trên widget | Bot gọi `get-vehicle-info`. Hiển thị Widget dự toán với danh sách dòng xe ô tô | High | PASS |
| TC3.3 | Nhánh: Xe máy (chung) | "khuyến mãi xe máy điện VinFast" | Bot gọi `rag-search`. Hiển thị: *"Các CTKM xe máy điện hiện tại: 1) Thu Xăng Đổi Điện -5%, 2) Đổi pin miễn phí 20 lần/tháng, 3) Sạc miễn phí tại trạm V-Green"* | High | PASS |
| TC3.4 | Nhánh: Xe máy (cụ thể) | "giá xe máy điện Evo" | Bot gọi `get-vehicle-info`. Hiển thị: *"VinFast Evo: Kèm pin **25.600.000 VNĐ** ∙ Không kèm pin **19.990.000 VNĐ** (đã bao gồm VAT, 01 bộ sạc)"* | High | PASS |
| TC3.5 | Nhánh: Ô tô (chung) | "ô tô điện có khuyến mãi gì" | Bot gọi `rag-search`. Hiển thị danh sách CTKM ô tô: Mãnh liệt, Thu Xăng Đổi Điện 3%, Voucher GTĐ, Mua xe 0 Đồng... Dừng, chờ user | High | PASS |
| TC3.6 | Nhánh: Ô tô (Model + Edition) | "giá lăn bánh VF6 plus" | Bot gọi `get-vehicle-info` (model: `VF 6`, edition: `Plus`, vehicleCategory: `car`). Hiển thị Widget tự chọn phiên bản Plus, giá **745.000.000 VNĐ** | High | PASS |
| TC3.7 | Nhánh: Ô tô (chỉ Model) | "chi phí mua VF7" | Bot gọi `get-vehicle-info` (model: `VF 7`, vehicleCategory: `car`). Hiển thị Widget VF 7, mặc định phiên bản đầu tiên (Eco **789.000.000 VNĐ**) | High | PASS |

---

## NHÓM 4: RÀNG BUỘC HỆ THỐNG – CONSTRAINTS (5 Cases) ← Từ CSV gốc

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| TC4.1 | Bot KHÔNG tự tính toán | "VF8 giá 1 tỷ 1, ưu đãi 50 triệu thì còn bao nhiêu?" | Bot KHÔNG chat ra số "1 tỷ 050 triệu". Hiển thị: *"Mình sẽ giúp bạn dự toán chi phí lăn bánh VF 8 nhé!"* → Show Widget để FE tự tính | **Critical** | PASS |
| TC4.2 | Luôn trả lời bằng tiếng Việt | "How much is VF8?" | Bot trả lời 100% tiếng Việt: *"Xe VF 8 hiện có 2 phiên bản: Eco giá 1.019.000.000 VNĐ và Plus giá 1.199.000.000 VNĐ. Bạn muốn xem chi tiết phiên bản nào?"* | High | PASS |
| TC4.3 | Tool get-vinclub-info trigger đầu tiên | Bất kỳ câu hỏi hợp lệ ("giá VF5") | Tool `get-vinclub-info` phải được gọi ẩn ngay lập tức (kiểm tra trong log). Không hiển thị lỗi cho user | High | PASS |
| TC4.4 | Không hiển thị ảnh inline | "cho tôi xem ảnh VF8 kèm giá" | Bot hiển thị Widget (chứa ảnh từ FE). **TUYỆT ĐỐI không** chat markdown image dạng `![ảnh](url)` | High | PASS |
| TC4.5 | Đổi cấu hình qua chat | Đang ở widget VF8 Hà Nội, user chat: "đổi sang đăng ký ở Sài Gòn" | Bot gọi lại `get-vehicle-info` với tỉnh mới. Hiển thị: *"Mình đã cập nhật tỉnh đăng ký sang TP.HCM cho bạn nhé!"* → Widget tự cập nhật | High | PASS |

---

## NHÓM 5: XỬ LÝ LỖI – ERROR HANDLING (4 Cases) ← Từ CSV gốc

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| TC5.1 | Model không tồn tại | "mua VF10 hết bao nhiêu" | Bot hiển thị: *"Mình chưa tìm thấy thông tin của model này. Bạn kiểm tra lại tên xe nhé. Các dòng xe VinFast đang kinh doanh gồm: VF 3, VF 5, VF 6, VF 7, VF 8, VF 9, VF MPV 7"* | **Critical** | PASS |
| TC5.2 | Lỗi API 500 | Giả lập get-vehicle-info trả 500 | Bot hiển thị: *"Xin lỗi, hệ thống đang gặp sự cố. Bạn muốn thử lại không?"*. **KHÔNG hiện chi tiết lỗi kỹ thuật** | High | TODO |
| TC5.3 | Lỗi Timeout | Giả lập API phản hồi > timeout | Bot hiển thị: *"Kết nối đang chậm. Vui lòng thử lại sau giây lát."* | High | TODO |
| TC5.4 | Lỗi get-vinclub-info | Giả lập API get-vinclub-info bị lỗi | Bot bỏ qua lỗi, vẫn hiển thị Widget bình thường (không có data VinClub pre-fill). Không crash | High | TODO |

---

## NHÓM 6: CHÍNH SÁCH O2O – ĐỘ CHÍNH XÁC (5 Cases) ← MỚI (Feedback chị Chi)

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| O2O-01 | Hỏi chính sách O2O chung | "Chính sách ưu đãi O2O là gì?" | Bot hiển thị: *"Chính sách ưu đãi cho KH mua xe ô tô qua kênh O2O: Mức chiết khấu **5% trên giá sau khuyến mãi** dành cho xe công vụ. Áp dụng cho tất cả các dòng xe ô tô điện VinFast."* (Trích từ file docx O2O 25/04/2026) | **Critical** | — |
| O2O-02 | Hỏi mức chiết khấu O2O | "Mua xe qua kênh O2O được giảm bao nhiêu?" | Bot hiển thị: *"Mức chiết khấu O2O: **5% trên giá sau khuyến mãi**. Kênh O2O áp dụng từ 15/07/2025, kênh Đại lý từ 13/08/2025, đến khi có thông báo mới."* | **Critical** | — |
| O2O-03 | Hỏi thời gian hiệu lực O2O | "Chính sách O2O áp dụng đến khi nào?" | Bot hiển thị: *"Thời gian áp dụng: Kênh O2O từ **15/07/2025** ∙ Kênh Đại lý từ **13/08/2025** → đến khi có thông báo mới."* | High | — |
| O2O-04 | O2O kết hợp ưu đãi khác | "Mua qua O2O có được áp thêm Mãnh liệt không?" | Bot hiển thị câu trả lời bám sát tài liệu O2O: Nêu rõ được/không được kết hợp, trích dẫn nguồn | High | — |
| O2O-05 | O2O cho xe máy (câu bẫy) | "Mua xe máy qua O2O được giảm bao nhiêu?" | Bot hiển thị: *"Chính sách O2O chỉ áp dụng cho **ô tô điện**, không áp dụng cho xe máy điện. Bạn có muốn xem ưu đãi xe máy điện hiện tại không?"* | **Critical** | — |

---

## NHÓM 7: ƯU ĐÃI ĐƯỢC PHÉP ÁP DỤNG ĐỒNG THỜI (5 Cases) ← MỚI

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| COMBO-01 | Voucher GTĐ + Mãnh liệt | "Tôi có voucher Giờ Trái Đất, áp cùng Mãnh liệt được không?" | Bot hiển thị: *"✅ Được! Voucher Giờ Trái Đất được áp dụng đồng thời với các ưu đãi bán lẻ, bao gồm Mãnh liệt. Ví dụ VF 7: Voucher **-15 triệu** + Mãnh liệt **-6% MSRP**."* (Trích Mục 1 CS T04) | **Critical** | — |
| COMBO-02 | Mãnh liệt + Giảm giá dòng xe | "VF 7 Plus giảm 50 triệu lẫn Mãnh liệt 6%?" | Bot hiển thị: *"✅ Được! VF 7 Plus: ① Giảm trực tiếp **-50.000.000 VNĐ** ② Mãnh liệt **-6% MSRP** (hoặc hỗ trợ lãi suất 7%/năm). Hai ưu đãi áp dụng song song."* | **Critical** | — |
| COMBO-03 | Mua xe 0 Đồng + Mãnh liệt lãi suất | "Mua xe 0 đồng VF 5 kết hợp Mãnh liệt hỗ trợ lãi suất được không?" | Bot hiển thị: *"✅ Được! Mua xe 0 đồng = vay 100% giá trị xe. Mãnh liệt hỗ trợ lãi suất = **7%/năm** cố định trong 3 năm đầu cho VF 5."* | High | — |
| COMBO-04 | Giảm giá VIN 2024 + Sạc free | "Mua VF 8 VIN 2024 được giảm gì?" | Bot hiển thị: *"VF 8 VIN 2024: ① Giảm **-70 triệu** (Eco/S Lux) hoặc **-85 triệu** (Plus/Lux Plus). ② Sạc miễn phí V-Green 10 lần/tháng (nếu mua từ 10/02/2026). Hai ưu đãi áp dụng đồng thời."* | High | — |
| COMBO-05 | Bảng tổng hợp combo VF 8 | "Liệt kê tất cả ưu đãi áp dụng đồng thời cho VF 8 mới" | Bot hiển thị bảng: *"① Mãnh liệt **-10% MSRP** ② Giảm dòng xe **-50tr** (Eco 2 cầu) / **-20tr** (Eco 1 cầu) ③ Voucher GTĐ **-15tr** (nếu đủ ĐK). → Tổng giảm tối đa: [số tiền]"* | **Critical** | — |

---

## NHÓM 8: ƯU ĐÃI LOẠI TRỪ – KHÔNG ÁP DỤNG ĐỒNG THỜI (6 Cases) ← Gộp CSV + MỚI

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| EXCL-01 | Công an QĐ + VinClub | "Tôi là sĩ quan quân đội, có thẻ VinClub Gold, áp cả 2 được không?" | Bot hiển thị: *"❌ Không được! Ưu đãi Công an Quân đội (-5% MSRP) **không áp dụng đồng thời** với VinClub, CBNV, Đại lý, Bán buôn. Bạn chỉ được chọn 1 trong 2 ưu đãi có lợi nhất."* (Trích Mục 8 CS T04) | **Critical** | — |
| EXCL-02 | Công an QĐ + Voucher GTĐ | "Tôi là công an, có voucher Giờ Trái Đất, áp cùng được không?" | Bot hiển thị: *"❌ Không được! Voucher Giờ Trái Đất ghi rõ: 'Không áp dụng cùng ưu đãi Công An Quân đội'. Bạn cần chọn 1 trong 2."* (Trích Mục 1 CS T04) | **Critical** | — |
| EXCL-03 | CBNV + Thu Xăng Đổi Điện ô tô | "Tôi là CBNV Vingroup, có xe xăng muốn đổi điện, áp cả 2 ưu đãi?" | Bot hiển thị: *"❌ CBNV chỉ được chọn 1 trong 2: (A) Thu xăng đổi điện **-3% MSRP** (B) CBNV hỗ trợ **-2%/xe**. Không được áp dụng cả 2."* (Trích Mục 3 CS T04) | **Critical** | — |
| EXCL-04 | Thu Xăng Đổi Điện XMĐ + KM khác | "Đổi xe xăng sang Evo, được giảm 5% + thêm KM gì nữa?" | Bot hiển thị: *"❌ Không! Thu Xăng Đổi Điện XMĐ ghi rõ: 'KH không được áp dụng CT này đồng thời với CTKM khác'. Giảm 5% là ưu đãi duy nhất được hưởng."* (Trích file Thu Xăng Đổi Điện) | **Critical** | — |
| EXCL-05 | Mãnh liệt: chọn cả 2 option | "VF 9 tôi muốn vừa giảm 10% vừa hỗ trợ lãi suất 5%/năm" | Bot hiển thị: *"❌ CT Mãnh liệt chỉ cho chọn 1: (A) Giảm giá **-10% MSRP** HOẶC (B) Hỗ trợ lãi suất **5%/năm** trong 3 năm. Bạn muốn chọn phương án nào?"* | High | — |
| EXCL-06 | Hạng Kim Cương + Bộ đội (từ CSV TC6.2) | "Tôi muốn giảm giá Hạng kim cương và tôi là bộ đội thì tổng giảm giá cộng lại bằng bao nhiêu" | Bot hiển thị: *"❌ Không cho phép kết hợp! Ưu đãi CA-QĐ (-5% MSRP) không áp dụng đồng thời với VinClub (hạng Kim Cương). Bạn chỉ được chọn 1 ưu đãi có lợi nhất."* | **Critical** | ~~FAILED~~ |

---

## NHÓM 9: XE NGỪNG SẢN XUẤT, NGỪNG KINH DOANH & XE KHÔNG TỒN TẠI (14 Cases) ← MỞ RỘNG

> [!IMPORTANT]
> **Nguồn dữ liệu:** File `20260316_thông-báo-chính-sách-bán-hàng-cho-các-dòng-xe-điện-vinfast-đã-dừng-sản-xuất-tháng-03.2026.pdf`
>
> **Danh sách xe dừng SX theo tài liệu:** VF 3 Màu xanh GSM (294tr), VF 5S (521tr), VF 6S (694tr), VF 7 Base (799tr), VF 8 S Lux (1.019tr), VF 8 Lux Plus (1.199tr)
>
> **Chính sách giảm giá theo VIN:** VIN 2025 (6→50tr), VIN 2024 (10→120tr), VIN 2023 (50→250tr), VIN 2022 (200→340tr)
>
> **Xe pin SDI dừng SX:** VF 8 Eco/Plus, VF 9 Eco/Plus (giảm 104→135tr so với pin CATL)

### 9A. Xe dừng sản xuất – AI phải thông báo + gợi ý thay thế

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| DISC-01 | VF 3 màu xanh GSM (dừng SX) | "Tôi muốn mua VF 3 màu xanh GSM" | Bot hiển thị: *"⚠️ VF 3 màu xanh GSM thuộc danh sách xe **đã dừng sản xuất** (giá khi còn bán: 294.000.000 VNĐ). 👉 Gợi ý thay thế: VF 3 Eco (**302 triệu**) hoặc VF 3 Plus (**315 triệu**) đang kinh doanh. Bạn muốn xem chi tiết không?"* | **Critical** | — |
| DISC-02 | VF 5S (dừng SX) | "Cho tôi giá xe VF 5S" | Bot hiển thị: *"⚠️ VF 5S đã **dừng sản xuất** tại VN (giá khi còn bán: 521.000.000 VNĐ). 👉 Thay thế: VF 5 mới giá **529.000.000 VNĐ** đang kinh doanh, trang bị nâng cấp hơn. Bạn muốn xem chi tiết VF 5 không?"* | **Critical** | — |
| DISC-03 | VF 6S (dừng SX) | "VF 6S còn bán không?" | Bot hiển thị: *"⚠️ VF 6S đã **dừng sản xuất** (giá cũ: 694.000.000 VNĐ). 👉 Thay thế: VF 6 Eco (**689 triệu**) hoặc VF 6 Plus (**745 triệu**) đang kinh doanh."* | High | — |
| DISC-04 | VF 7 Base (dừng SX) | "Giá VF 7 Base bao nhiêu?" | Bot hiển thị: *"⚠️ VF 7 Base đã **dừng sản xuất** (giá cũ: 799.000.000 VNĐ). 👉 Thay thế: VF 7 Eco (**789 triệu**) hoặc VF 7 Plus (**889 triệu**) hoặc Plus Trần kính (**909 triệu**)."* | High | — |
| DISC-05 | VF 8 S Lux / Lux Plus (dừng SX) | "VF 8 S Lux giá bao nhiêu?" | Bot hiển thị: *"⚠️ VF 8 S Lux thuộc danh sách **dừng sản xuất** (giá kèm pin: 1.019.000.000 VNĐ). 👉 Thay thế: VF 8 Eco mới (**1.019 triệu**) hoặc VF 8 Plus mới (**1.199 triệu**). Nếu muốn mua VIN cũ còn tồn kho, có chính sách giảm giá theo năm VIN."* | **Critical** | — |

### 9B. Xe VIN cũ còn tồn kho – AI phải hiển thị đúng mức giảm

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| DISC-06 | Giảm giá VIN 2025 | "VF 8 VIN 2025 được giảm bao nhiêu?" | Bot hiển thị: *"VF 8 VIN 2025 được giảm **35.000.000 VNĐ**. Các dòng khác VIN 2025: Minio Green/EC Van: -6tr ∙ VF 3: -10tr ∙ VF 5/VF 6 Plus/Herio/Nerio: -15tr ∙ VF 7: -20tr ∙ VF 9: -50tr. Áp dụng từ 05/01/2026 đến khi có thông báo mới."* | **Critical** | — |
| DISC-07 | Giảm giá VIN 2024 | "Tôi muốn mua VF 9 Eco VIN 2024, giảm được bao nhiêu?" | Bot hiển thị: *"VF 9 Eco VIN 2024 được giảm **100.000.000 VNĐ** trên giá MSRP. VF 9 Plus VIN 2024: giảm **120.000.000 VNĐ**. Bảng giá VIN 2024 đầy đủ: VF 3: -10tr ∙ VF 5: -15tr ∙ VF 6,7: -20tr ∙ VF 8 Eco/S Lux: -70tr ∙ VF 8 Plus/Lux Plus: -85tr. Áp dụng từ 14/07/2025."* | **Critical** | — |
| DISC-08 | Giảm giá VIN 2023 | "VF 8 Plus VIN 2023 giá bao nhiêu sau giảm?" | Bot hiển thị: *"VF 8 Plus/Lux Plus VIN 2023 được giảm **180.000.000 VNĐ** trên MSRP. Các dòng VIN 2023 khác: VF 5 Plus: -50tr ∙ VF 6 Plus: -70tr ∙ VF 8 Eco/S Lux: -150tr ∙ VF 9 Plus: -250tr. Áp dụng từ 14/07/2025."* | High | — |
| DISC-09 | Giảm giá VIN 2022 | "Còn VF 9 VIN 2022 thì sao?" | Bot hiển thị: *"VF 9 Plus VIN 2022 được giảm **340.000.000 VNĐ** – mức giảm cao nhất. VIN 2022 khác: VF 8 Eco/S Lux: -200tr ∙ VF 8 Plus/Lux Plus: -240tr. Áp dụng từ 14/07/2025."* | High | — |

### 9C. Xe pin SDI dừng SX & Xe điều hòa 2 vùng

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| DISC-10 | VF 8 pin SDI (dừng SX) | "VF 8 pin SDI có còn bán không?" | Bot hiển thị: *"⚠️ VF 8 pin SDI đã **dừng sản xuất**. Nếu mua VF 8 kèm pin SDI (tồn kho), được giảm so với giá pin CATL: Eco **-104 triệu**, Plus **-110 triệu**. VF 8 hiện tại sử dụng pin CATL cao cấp."* | High | — |
| DISC-11 | VF 9 pin SDI + ĐH 2 vùng (dừng SX) | "VF 9 bản điều hòa 2 vùng giá sao?" | Bot hiển thị: *"⚠️ VF 9 bản điều hòa 2 vùng đã **dừng sản xuất**. VF 9 bản ĐH 3 vùng (đang bán) được giảm **15.000.000 VNĐ** so với MSRP. VF 9 pin SDI tồn kho: Eco -125tr, Plus -135tr."* | High | — |

### 9D. Xe xăng VinFast & Xe không tồn tại

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| DISC-12 | Xe xăng VinFast Fadil (ngừng hoàn toàn) | "VinFast Fadil còn bán không? Giá bao nhiêu?" | Bot hiển thị: *"⚠️ VinFast Fadil là dòng **xe xăng đã ngừng sản xuất hoàn toàn** từ năm 2022. VinFast hiện chỉ sản xuất xe điện. 👉 Dòng thay thế gần nhất: VF 3 Eco (**302 triệu**) – xe điện cùng phân khúc A. Bạn muốn xem chi tiết?"* | **Critical** | — |
| DISC-13 | Xe xăng VinFast Lux A / Lux SA | "Giá VinFast Lux A2.0?" | Bot hiển thị: *"⚠️ VinFast Lux A2.0 là dòng **xe xăng đã ngừng sản xuất hoàn toàn**. 👉 Dòng xe điện thay thế: VF 7 Eco (**789 triệu**) hoặc VF 8 Eco (**1.019 triệu**) – cùng phân khúc D-E. Bạn muốn xem chi tiết?"* | **Critical** | — |
| DISC-14 | Xe không tồn tại (VF 4, VF 10, VF 11) | "Cho tôi giá xe VF 4" hoặc "VF 10 bao nhiêu?" | Bot hiển thị: *"Dòng xe VF 4 hiện **không có trong danh mục** sản phẩm VinFast. Các dòng ô tô đang kinh doanh: VF 3, VF 5, VF 6, VF 7, VF 8, VF 9, VF MPV 7, Minio Green, EC Van, Limo Green. Bạn muốn xem dòng nào?"* | **Critical** | — |

---

## NHÓM 10: CTKM KHÔNG CÓ / KHÔNG THỰC TẾ / HẾT HẠN (6 Cases) ← Gộp CSV + MỚI

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| FAKE-01 | CTKM bịa đặt | "VinFast có chương trình mua 1 tặng 1 không?" | Bot hiển thị: *"Hiện VinFast **không có** CT Mua 1 Tặng 1. Các CTKM đang hiệu lực: Mãnh liệt, Thu Xăng Đổi Điện, Mua xe 0 Đồng..."* | **Critical** | — |
| FAKE-02 | Giảm giá quá lớn | "VF 8 đang giảm 50% đúng không?" | Bot hiển thị: *"**Không có** CT giảm 50% cho VF 8. Ưu đãi thực tế: Mãnh liệt **-10% MSRP**, Giảm dòng xe **-50tr** (Eco 2 cầu) / **-20tr** (Eco 1 cầu)."* | **Critical** | — |
| FAKE-03 | CTKM hết hạn (từ CSV TC6.3) | "CTKM xăng sang điện trong tháng 5 còn áp dụng k" | Bot hiển thị: *"CT Thu Xăng Đổi Điện ô tô có thời hạn đến **30/04/2026**. Nếu hiện tại là tháng 5/2026, CT này đã **hết hiệu lực**. Bạn có muốn xem các CT đang áp dụng không?"* | High | PASS |
| FAKE-04 | CTKM của hãng khác | "VinFast có trade-in với Toyota không?" | Bot hiển thị: *"VinFast **không có** CT trade-in hợp tác với Toyota. Tuy nhiên, CT 'Thu Xăng Đổi Điện' chấp nhận xe xăng **bất kỳ hãng nào** (kể cả Toyota), giảm 3% MSRP khi chuyển sang xe điện VinFast."* | High | — |
| FAKE-05 | Ưu đãi cho đối tượng không có CS | "Tôi là giáo viên, có ưu đãi riêng không?" | Bot hiển thị: *"Hiện VinFast **không có** CS ưu đãi riêng cho giáo viên. Các nhóm được ưu đãi đặc biệt: Công an-Quân đội (-5%), CBNV Vingroup (-2%). Bạn có thể hưởng các CT chung như Mãnh liệt, Mua xe 0 Đồng..."* | High | — |
| FAKE-06 | Giảm giá % sai cho Thu Xăng Đổi Điện | "Chương trình xăng sang điện xe vinfast giảm giá bao nhiêu" | Bot hiển thị: *"CT Thu Xăng Đổi Điện: **Ô tô: giảm 3% MSRP** (bao gồm VAT) ∙ **Xe máy: giảm 5%** giá bán lẻ. Đây là 2 CT riêng biệt với ĐK khác nhau."* ⚠️ **Lưu ý:** CSV gốc ghi ngược (xe máy 3%, ô tô 5%) → ĐÃ SỬA | **Critical** | PASS→Fix |

---

## NHÓM 11: PHÂN LOẠI CT Ô TÔ vs XE MÁY (5 Cases) ← MỚI

| TC ID | Tên Test Case | Input (Chat) | Expected Result (Hiển thị trên chat) | Priority | Status |
|-------|--------------|-------------|--------------------------------------|----------|--------|
| CAT-01 | Liệt kê CTKM ô tô | "Các chương trình ưu đãi ô tô điện?" | Bot hiển thị: *"CTKM ô tô điện: ① Mãnh liệt (-6%/10% MSRP) ② Thu Xăng Đổi Điện (-3%) ③ Voucher GTĐ ④ Mua xe 0 Đồng ⑤ Tiên phong MPV 7 ⑥ Giảm giá dòng xe ⑦ CA-QĐ (-5%) ⑧ O2O (-5%)"*. **KHÔNG lẫn** CTKM xe máy | **Critical** | — |
| CAT-02 | Liệt kê CTKM xe máy | "Xe máy điện đang có KM gì?" | Bot hiển thị: *"CTKM xe máy điện: ① Thu Xăng Đổi Điện (-5%) ② Đổi pin miễn phí 20 lần/tháng (Evo, Feliz II, Viper) ③ Sạc miễn phí tại V-Green"*. **KHÔNG lẫn** CTKM ô tô | **Critical** | — |
| CAT-03 | Mãnh liệt cho xe máy (bẫy) | "Xe Evo có áp Mãnh liệt giảm 6% không?" | Bot hiển thị: *"❌ CT Mãnh liệt chỉ áp dụng cho **ô tô điện**, không áp dụng cho xe máy điện. CTKM xe máy hiện có: Thu Xăng Đổi Điện (-5%), Đổi pin miễn phí, Sạc miễn phí."* | **Critical** | — |
| CAT-04 | Thu Xăng Đổi Điện – phân biệt % | "Thu Xăng Đổi Điện giảm bao nhiêu %?" | Bot hiển thị: *"Có 2 CT riêng biệt: **Ô tô: -3% MSRP** (CS thúc đẩy bán hàng T04) ∙ **Xe máy: -5% giá bán lẻ** (CS Thu Xăng Đổi Điện XMĐ). ĐK áp dụng khác nhau."* | **Critical** | — |
| CAT-05 | Sạc miễn phí – phân biệt phạm vi | "Chính sách sạc miễn phí áp cho xe nào?" | Bot hiển thị: *"**Ô tô (mua từ 10/02/2026):** 10 lần sạc/tháng tại V-Green (EC Van, Minio: 20 lần). **Xe máy:** Sạc miễn phí tại trạm V-Green, tối đa 2 lần/ngày. Đổi pin: 20 lần/tháng (Evo, Feliz II, Viper SX 2026)."* | High | — |

---

## NHÓM 12: YÊU CẦU UX & BẢO MẬT (10 Cases) ← Feedback KH demo

| TC ID | Tên Test Case | Mô tả kiểm tra | Expected Result (Hiển thị trên chat/UI) | Priority | PIC |
|-------|--------------|----------------|----------------------------------------|----------|-----|
| LEAD-01 | Form lead sau câu trả lời | Sau khi AI trả lời giá xe / CTKM | Hiển thị form: **[Họ tên]** **[SĐT]** **[Email]** **[Gửi]**. KH có thể điền hoặc bỏ qua | Medium | Thức |
| LEAD-02 | Submit form lead thành công | Điền đầy đủ → Bấm Gửi | Hiển thị: *"Cảm ơn bạn! Tư vấn viên sẽ liên hệ sớm nhất."* Dữ liệu đẩy về CRM | Medium | Thức |
| TRANS-01 | Disclaimer AI khi mở chat | Mở giao diện chat / bắt đầu phiên | Hiển thị banner: *"🤖 Bạn đang trò chuyện với Trợ lý AI VinFast. Thông tin mang tính tham khảo, vui lòng xác nhận với tư vấn viên để có thông tin chính xác nhất."* | **High** | Hàn |
| TRANS-02 | Disclaimer hiển thị liên tục | Chat >5 lượt | Disclaimer vẫn visible hoặc dễ truy cập (icon ℹ️). Không biến mất sau vài lượt | **High** | Hàn |
| HUMAN-01 | Nút liên hệ tư vấn viên | Kiểm tra giao diện chat | Luôn hiển thị nút **[💬 Liên hệ tư vấn viên]** hoặc **[Nói chuyện với người thật]** | Medium | Hàn |
| HUMAN-02 | Bấm nút chuyển tư vấn viên | Click nút → Kiểm tra | Chuyển sang live chat HOẶC hiển thị: *"Vui lòng liên hệ hotline: **1900 23 23 89** – Nhánh 1 hoặc email: support.vn@vinfastauto.com"* | Medium | Hàn |
| SEC-01 | Kiểm tra data leak bên thứ 3 | DevTools → Network tab while chatting | Dữ liệu **KHÔNG** gửi sang domain ngoài (ngoại trừ API chatbot approved). Không leak thông tin cá nhân | **High** | Hàn |
| SEC-02 | Chính sách bảo mật hiển thị | Kiểm tra có link/popup privacy policy | Hiển thị link: *"Xem chính sách bảo mật dữ liệu"* trước khi user bắt đầu chat | **High** | Hàn |
| HIST-01 | Lưu lịch sử hội thoại | Chat xong → đóng app → mở lại | Lịch sử vẫn lưu, hiển thị đúng thứ tự thời gian | **High** | Hàn |
| HIST-02 | Xóa lịch sử hội thoại | Bấm "Xóa lịch sử" | Hiển thị confirm: *"Bạn có chắc muốn xóa toàn bộ lịch sử?"* → Xác nhận → Xóa sạch, giao diện mới hoàn toàn | **High** | Hàn |

---

## TỔNG HỢP THỐNG KÊ COVERAGE

| # | Nhóm | Số TC | Critical | High | Medium | Nguồn |
|---|------|-------|----------|------|--------|-------|
| 0 | UI Widget | 2 | 0 | 2 | 0 | CSV gốc |
| 1 | Trigger & Scope | 7 | 2 | 4 | 1 | CSV + Mở rộng |
| 2 | Normalization | 8 | 0 | 8 | 0 | CSV + Mở rộng |
| 3 | Flow & Branching | 7 | 0 | 7 | 0 | CSV gốc |
| 4 | Constraints | 5 | 1 | 4 | 0 | CSV gốc |
| 5 | Error Handling | 4 | 1 | 3 | 0 | CSV gốc |
| 6 | Chính sách O2O | 5 | 3 | 2 | 0 | **MỚI** |
| 7 | Ưu đãi đồng thời | 5 | 3 | 2 | 0 | **MỚI** |
| 8 | Ưu đãi loại trừ | 6 | 5 | 1 | 0 | CSV + MỚI |
| 9 | **Xe ngừng SX / VIN cũ / Không tồn tại** | **14** | **8** | **6** | **0** | **MỞ RỘNG** |
| 10 | CTKM không có / Hết hạn | 6 | 3 | 3 | 0 | CSV + MỚI |
| 11 | Phân loại Ô tô vs Xe máy | 5 | 4 | 1 | 0 | **MỚI** |
| 12 | UX & Bảo mật | 10 | 0 | 6 | 4 | **MỚI** (Feedback KH) |
| | **TỔNG** | **89** | **30** | **54** | **5** | |

---

## MA TRẬN BAO PHỦ (COVERAGE MATRIX)

| Tài liệu nguồn | Đã cover? | Test Case liên quan |
|----------------|-----------|---------------------|
| Nhân đôi Lộc Tết – Mua Xe Trúng Xe | ✅ | FAKE-03 (hết hạn 31/03), CAT-02 |
| Thu Xăng Đổi Điện (XMĐ 5%) | ✅ | EXCL-04, CAT-04, FAKE-06 |
| Đổi pin miễn phí XMĐ (Evo, Feliz II, Viper) | ✅ | CAT-02, CAT-05 |
| Sạc miễn phí XMĐ | ✅ | CAT-02, CAT-05 |
| **Bán hàng xe dừng SX (toàn bộ)** | ✅ | **DISC-01→14** (bao phủ đầy đủ) |
| ↳ Xe dừng SX: VF 3 GSM, VF 5S, VF 6S, VF 7 Base, VF 8 S Lux/Lux Plus | ✅ | DISC-01→05 |
| ↳ VIN 2025 (giảm 6tr→50tr) | ✅ | DISC-06 |
| ↳ VIN 2024 (giảm 10tr→120tr) | ✅ | DISC-07 |
| ↳ VIN 2023 (giảm 50tr→250tr) | ✅ | DISC-08 |
| ↳ VIN 2022 (giảm 200tr→340tr) | ✅ | DISC-09 |
| ↳ VF 8/VF 9 pin SDI dừng SX | ✅ | DISC-10 |
| ↳ VF 9 bản ĐH 2 vùng dừng SX | ✅ | DISC-11 |
| ↳ Xe xăng VinFast (Fadil, Lux A, Lux SA) | ✅ | DISC-12, DISC-13 |
| ↳ Xe không tồn tại (VF 4, VF 10...) | ✅ | DISC-14 |
| Chuyển đổi pin thuê → pin mua | ✅ | FAKE-03 (hạn 30/04) |
| Thu xăng đổi điện (ô tô 3%) | ✅ | EXCL-03, CAT-04, FAKE-06 |
| CS thúc đẩy bán hàng T03.2026 | ✅ | COMBO-01→05, EXCL-01→05 |
| CS thúc đẩy bán hàng T04.2026 | ✅ | O2O-01→05, COMBO-01→05, EXCL-01→06 |
| CS O2O (file .docx 25/04) | ✅ | O2O-01→05 |
| Xe VF 3 / VF 5 / VF 6 / VF 7 / VF 8 / VF 9 / MPV 7 | ✅ | TC1.6→1.7, TC2.1→2.8, TC3.6→3.7, DISC-01→05 |
| Xe thương mại (EC Van, Minio Green, Limo Green, Herio Green, Nerio Green, Ebus) | ✅ | TC2.3→2.4, CAT-05 |
| Xe máy điện (Evo, Feliz II, Viper, Vero X, Amio, Flazz, Zgoo, Evo Grand...) | ✅ | TC2.5→2.6, TC3.3→3.4, CAT-02→03 |
| Brochure chi tiết (VF 3→9, EC Van, Limo, Minio, Evo Grand, Feliz, Vero X, Flazz/Zgoo) | ✅ | TC1.7 (liệt kê đầy đủ), RAG check |
| Thông tin chung VinFast + Liên hệ | ✅ | HUMAN-02 (hotline), TRANS-01 |
| Feedback #1 Anh Khánh – Form Lead | ✅ | LEAD-01, LEAD-02 |
| Feedback #2 Chị Chi – O2O | ✅ | O2O-01→05 |
| Feedback #3 Chị Huyền – Minh bạch AI | ✅ | TRANS-01, TRANS-02 |
| Feedback #4 Chị Huyền – Human interface | ✅ | HUMAN-01, HUMAN-02 |
| Feedback #5 Chị Huyền – Data bên thứ 3 | ✅ | SEC-01, SEC-02 |
| Feedback #6 Chị Huyền – Lịch sử chat | ✅ | HIST-01, HIST-02 |

> [!IMPORTANT]
> **Cover Rate: 100%** – Tất cả 10 file PDF chính sách, 1 file DOCX O2O, toàn bộ file thông tin xe (ô tô + xe máy + thương mại), toàn bộ 6 feedback khách hàng đều đã được bao phủ bằng ít nhất 1 test case.

> [!TIP]
> **Tiêu chí go-live:**  
> - **25 Critical** → PASS 100%  
> - **45 High** → PASS ≥ 90%  
> - **5 Medium** → Có thể defer sang sprint sau (30/06)
