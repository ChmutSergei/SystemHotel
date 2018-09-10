package by.chmut.hotel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Reservation {
    private int id;
    private int userId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDate date; // String date in format YYYY MM DD HH MM

    public Reservation(int userId, int roomId, LocalDate checkIn, LocalDate checkOut, LocalDate date) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        if (id != that.id || userId != that.userId || roomId != that.roomId) {
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
        if (date == null) {
            if (that.date != null) {
                return false;
            }
        } else if (!date.equals(that.date)) {
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
        result = result * 37 + (date == null ? 0 : date.hashCode()) * result;
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", date=" + date +
                '}';
    }
}
