import { Link } from "react-router-dom";
import { Bell, CircleUserRound } from "lucide-react";
import heroImg from "../assets/images/hero-lamine.webp";
import logoAmixi from "../assets/images/logo-amixi.png";
import { useState } from "react";

const featureCards = [
  "Đặt sân trực tuyến",
  "Theo dõi lịch thi đấu",
  "Quản lý đơn đặt sân",
];

function Notification({ message, type, onClose }: { message: string; type: 'success' | 'error'; onClose: () => void }) {
  return (
    <div className={`fixed top-6 right-6 z-50 px-6 py-3 rounded shadow-lg text-white ${type === 'success' ? 'bg-green-600' : 'bg-red-600'}`}>
      <span>{message}</span>
      <button className="ml-4 font-bold" onClick={onClose}>×</button>
    </div>
  );
}

function BookNowModal({ open, onClose, onConfirm, loading }: {
  open: boolean;
  onClose: () => void;
  onConfirm: (field: string, slot: string) => void;
  loading: boolean;
}) {
  const fields = ["Sân 1", "Sân 2", "Sân 3", "Sân 4"];
  const slots = [
    "8:00 - 9:30",
    "9:30 - 11:00",
    "15:00 - 16:30",
    "16:30 - 18:00",
    "18:00 - 19:30",
  ];
  const [field, setField] = useState(fields[0]);
  const [slot, setSlot] = useState(slots[0]);
  const price = 100000;
  if (!open) return null;
  return (
    <div className="fixed inset-0 z-40 flex items-center justify-center bg-black/40">
      <div className="bg-white rounded-lg p-6 min-w-[320px] max-w-[90vw]">
        <h3 className="text-lg font-bold mb-4">Đặt sân nhanh</h3>
        <div className="mb-3">
          <label className="block mb-1 font-semibold">Chọn sân</label>
          <select className="w-full border rounded px-2 py-1" value={field} onChange={e => setField(e.target.value)}>
            {fields.map(f => <option key={f} value={f}>{f}</option>)}
          </select>
        </div>
        <div className="mb-3">
          <label className="block mb-1 font-semibold">Chọn khung giờ</label>
          <select className="w-full border rounded px-2 py-1" value={slot} onChange={e => setSlot(e.target.value)}>
            {slots.map(s => <option key={s} value={s}>{s}</option>)}
          </select>
        </div>
        <div className="mb-4 text-base font-semibold">Tổng tiền: <span className="text-[#2E9B3F]">{price.toLocaleString()}₫</span></div>
        <div className="flex gap-3 justify-end">
          <button onClick={onClose} className="px-4 py-2 rounded bg-gray-200 hover:bg-gray-300">Hủy</button>
          <button onClick={() => onConfirm(field, slot)} className="px-4 py-2 rounded bg-[#2E9B3F] text-white hover:bg-[#217a31]" disabled={loading}>
            {loading ? "Đang đặt..." : "Xác nhận"}
          </button>
        </div>
      </div>
    </div>
  );
}

export function LandingPage() {
  const [showModal, setShowModal] = useState(false);
  const [notification, setNotification] = useState<{ message: string; type: 'success' | 'error' } | null>(null);
  const [bookingLoading, setBookingLoading] = useState(false);

  const handleBookNow = async (field: string, slot: string) => {
    setBookingLoading(true);
    try {
      // Replace with your actual booking API endpoint and payload
      const res = await fetch("/api/fields/book", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          date: new Date().toLocaleDateString("en-GB"),
          slots: [{ court: field, slot }],
          totalPrice: 100000,
        }),
      });
      if (!res.ok) throw new Error("Đặt sân thất bại. Vui lòng thử lại.");
      setNotification({ message: "Đặt sân thành công!", type: "success" });
      setShowModal(false);
    } catch (err: any) {
      setNotification({ message: err.message || "Có lỗi xảy ra!", type: "error" });
    } finally {
      setBookingLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#005E2E] to-[#29721D]">
      <header className="border-b border-white/15 bg-[#005E2E]/55 backdrop-blur">
        <div className="mx-auto flex w-full max-w-[1280px] items-center justify-between gap-5 px-4 py-4 sm:px-6 lg:px-8">
          <div className="flex items-center gap-3">
            <img
              src={logoAmixi}
              alt="MIXIFOOT logo"
              className="h-10 w-auto object-contain sm:h-12"
            />
            <div>
              <p className="text-sm font-medium text-white/80">Công ty AMIXI</p>
              <p
                className="text-4xl leading-none tracking-widest text-white"
                style={{ fontFamily: '"Jersey 10", sans-serif' }}
              >
                MIXIFOOT
              </p>
            </div>
          </div>

          <nav className="hidden items-center gap-10 text-lg font-semibold text-white lg:flex xl:gap-14">
            <Link to="/" className="transition hover:text-white/75">
              Home
            </Link>
            <Link to="/booking" className="transition hover:text-white/75">
              Đặt sân
            </Link>
            <Link to="/match" className="transition hover:text-white/75">
              Chợ kèo
            </Link>
            <Link to="/profile" className="transition hover:text-white/75">
              Hồ sơ
            </Link>
          </nav>

          <div className="flex items-center gap-3 sm:gap-4">
            <button
              type="button"
              className="inline-flex h-11 w-11 items-center justify-center rounded-full border border-white/25 bg-white/10 text-white transition hover:bg-white/16"
              aria-label="Thông báo"
            >
              <Bell size={20} />
            </button>
            <button
              type="button"
              className="inline-flex h-11 w-11 items-center justify-center rounded-full border border-white/25 bg-white/10 text-white transition hover:bg-white/16"
              aria-label="Tài khoản"
            >
              <CircleUserRound size={22} />
            </button>
            <Link
              to="/login"
              className="inline-flex items-center rounded-md bg-[#29721D] px-5 py-2.5 text-sm font-semibold text-white transition hover:bg-[#225f19]"
            >
              Đăng nhập
            </Link>
          </div>
        </div>
      </header>

      <section className="relative h-[90vh] min-h-[620px] w-full overflow-hidden">
        <img
          src={heroImg}
          alt="MIXIFOOT hero"
          loading="eager"
          className="h-full w-full object-cover object-center"
        />
        <div className="absolute inset-0 bg-[#05110A]/45" />

        <div className="absolute inset-0 flex items-start p-6 sm:p-10 lg:p-14">
          <div className="mt-14 flex max-w-[560px] flex-col items-start gap-10 sm:mt-16 lg:mt-20">
            <h2 className="text-3xl font-bold uppercase leading-[1.1] text-white md:text-5xl lg:text-6xl">
              CHÁY CÙNG
              <br />
              NIỀM ĐAM
              <br />
              MÊ BÓNG
              <br />
              ĐÁ!
            </h2>
            {/* Nút \"Đặt sân ngay\" mở modal */}
            <button
              className="inline-flex rounded-md border-4 border-[#2D0B0B] bg-white px-16 py-6 text-3xl font-bold text-[#1E160C] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.4)] transition-transform duration-200 hover:scale-105 hover:brightness-95 md:text-4xl"
              onClick={() => setShowModal(true)}
            >
              Đặt sân ngay !
            </button>
          </div>
        </div>
        <BookNowModal
          open={showModal}
          onClose={() => setShowModal(false)}
          onConfirm={handleBookNow}
          loading={bookingLoading}
        />
        {notification && (
          <Notification
            message={notification.message}
            type={notification.type}
            onClose={() => setNotification(null)}
          />
        )}
      </section>

      <main className="mx-auto w-full max-w-[1280px] px-4 py-6 sm:px-6 lg:px-8 lg:py-8">
        <section className="mt-2 rounded-[20px] border border-white/15 bg-[#005E2E]/32 p-5 shadow-[0px_4px_4px_0px_rgba(0,0,0,0.4)] sm:p-7">
          <div className="flex flex-col items-center gap-3 text-center">
            <h3 className="text-3xl font-bold uppercase text-white sm:text-4xl">
              Chức năng chính
            </h3>
            <div className="h-[2px] w-[180px] bg-white/70" />
            <p className="text-sm text-white/80">
              Nền tảng quản lý tập trung cho vận hành sân bóng và đặt sân theo
              thời gian thực.
            </p>
          </div>

          <div className="mt-6 space-y-7">
            <div>
              <div className="inline-flex rounded-xl bg-[#84e30f] px-5 py-2.5">
                <span className="text-2xl font-bold text-[#0d0355]">
                  Phần mềm quản lý
                </span>
              </div>

              <div className="mt-5 grid gap-4 sm:grid-cols-2 xl:grid-cols-3">
                {featureCards.map((title) => (
                  <article
                    key={title}
                    className="flex h-[210px] flex-col justify-between rounded-[20px] border-2 border-black/55 bg-white/95 p-5 shadow-[0px_4px_4px_0px_rgba(0,0,0,0.4)]"
                  >
                    <h4 className="text-xl font-bold text-[#0d0355]">{title}</h4>
                    <p className="text-sm text-[#113520]">
                      Dữ liệu realtime, dễ vận hành và đồng bộ xuyên suốt khu sân.
                    </p>
                  </article>
                ))}
              </div>
            </div>

            <div>
              <div className="inline-flex rounded-xl bg-[#84e30f] px-5 py-2.5">
                <span className="text-2xl font-bold text-[#0d0355]">
                  Đội ngũ AMIXI
                </span>
              </div>

              <div className="mt-5 grid gap-4 xl:grid-cols-[320px_minmax(0,1fr)]">
                <article className="h-[230px] rounded-[20px] border-2 border-black/55 bg-white/95 p-5 shadow-[0px_4px_4px_0px_rgba(0,0,0,0.4)]">
                  <h4 className="text-xl font-bold text-[#0d0355]">Vận hành trực quầy</h4>
                  <p className="mt-2 text-sm text-[#113520]">
                    Nhóm hỗ trợ check-in, xử lý ca, khóa sân bảo trì và điều phối lịch.
                  </p>
                </article>

                <article className="h-[230px] rounded-[20px] border-2 border-black/55 bg-white/95 p-5 shadow-[0px_4px_4px_0px_rgba(0,0,0,0.4)]">
                  <h4 className="text-xl font-bold text-[#0d0355]">Điều hành chuỗi khu sân</h4>
                  <p className="mt-2 text-sm text-[#113520]">
                    Theo dõi doanh thu, nhân sự và hiệu suất các khu sân trên cùng một màn hình.
                  </p>
                </article>
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}