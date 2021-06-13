package com.example.campingapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservationSystem {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();


    public void reserveCamp(CampingEntity camp, CalendarDay startDay, CalendarDay endDay, int people){
        String userId = user.getUid();
        int price = (camp.getPrice() + camp.getAddPrice()*(people-camp.getMinPeople()));
        //ReservationEntity reservation = new ReservationEntity(userId,camp.getId(),startDay,endDay,people,price);
        ReservationEntity reservation = new ReservationEntity();
        reservation.setUserId(userId);
        reservation.setCampId(camp.getId());
        reservation.setStartDay(calToString(startDay));
        reservation.setEndDay(calToString(endDay));
        reservation.setPeople(people);
        reservation.setPrice(price);
        firebaseDb.child("reservation").child(camp.getId()).child(userId).setValue(reservation);
    }

    public void blockDate(CampingEntity camp, MaterialCalendarView view) {
        firebaseDb.child("reservation").child(camp.getId()).addValueEventListener(new ValueEventListener() {
            ArrayList<ReservationEntity> reservations = new ArrayList<ReservationEntity>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                    ReservationEntity reservation = dSnap.getValue(ReservationEntity.class);
                    if (reservation == null){
                        System.out.println("null");
                    }else {
                        System.out.println(reservation.getStartDay());
                    }
                    LocalDate start = LocalDate.parse(reservation.getStartDay(),DateTimeFormatter.ISO_DATE);
                    LocalDate end = LocalDate.parse(reservation.getEndDay(),DateTimeFormatter.ISO_DATE);
                    view.addDecorator(new DayDecorator(CalendarDay.from(start),CalendarDay.from(end)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private class DayDecorator implements DayViewDecorator {
        CalendarDay start;
        CalendarDay end;


        public DayDecorator(CalendarDay start, CalendarDay end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.isInRange(start,end);

        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);

        }
    }
    public String calToString(CalendarDay calendarDay){
        LocalDate date = calendarDay.getDate();
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.toString();

    }
}
