package com.kstn.group4.backend.booking.dto.player;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CreateBookingRequest {

        @NotNull(message = "pitchId không được để trống")
        private Integer pitchId;

        @NotNull(message = "bookingDate không được để trống")
        @FutureOrPresent(message = "bookingDate phải từ hôm nay trở đi")
        private LocalDate bookingDate;

        @NotNull(message = "slotNumber không được để trống")
        @Min(value = 1, message = "slotNumber phải từ 1 đến 11")
        @Max(value = 11, message = "slotNumber phải từ 1 đến 11")
        private Integer slotNumber;

        public Integer getPitchId() {
                return pitchId;
        }

        public void setPitchId(Integer pitchId) {
                this.pitchId = pitchId;
        }

        public LocalDate getBookingDate() {
                return bookingDate;
        }

        public void setBookingDate(LocalDate bookingDate) {
                this.bookingDate = bookingDate;
        }

        public Integer getSlotNumber() {
                return slotNumber;
        }

        public void setSlotNumber(Integer slotNumber) {
                this.slotNumber = slotNumber;
        }
}
