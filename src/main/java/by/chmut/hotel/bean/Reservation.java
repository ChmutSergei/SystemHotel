package by.chmut.hotel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Reservation {
    private int id;
    private int userId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDateTime dateTime; // date in format YYYY MM DD HH MM
    private int payment; //  0 - unpaid  |  1 - paid

    public Reservation(int userId, int roomId, LocalDate checkIn, LocalDate checkOut) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        if (id != that.id || userId != that.userId || roomId != that.roomId || payment != that.payment) {
            return false;
        }
        if (checkIn == null) {
            if (that.checkIn != null) {
                return false;
            }
        } else if (!checkIn.equals(that.checkIn)) {
            return false;
        }
        if (checkOut == null) {
            if (that.checkOut != null) {
                return false;
            }
        } else if (!checkOut.equals(that.checkOut)) {
            return false;
        }
        if (dateTime == null) {
            if (that.dateTime != null) {
                return false;
            }
        } else if (!dateTime.equals(that.dateTime)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 37 + id;
        result = result * 37 + userId;
        result = result * 37 + roomId;
        result = result * 37 + payment;
        result = result * 37 + (checkIn == null ? 0 : checkIn.hashCode()) * result;
        result = result * 37 + (checkOut == null ? 0 : checkOut.hashCode()) * result;
        result = result * 37 + (dateTime == null ? 0 : dateTime.hashCode()) * result;
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", dateTime=" + dateTime +
                ", payment=" + payment +
                '}';
    }
}
