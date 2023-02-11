package com.link_intersystems.time;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public enum PickupMinute {
    ZERO(0), THIRTY(30);

    private static List<PickupMinute> VALUES = Arrays.asList(values());

    static {
        VALUES.sort((pm1, pm2) -> Integer.compare(pm1.minute, pm2.minute));
    }

    public static PickupMinute closestPickupMinute(LocalTime time) {
        int minute = time.getMinute();

        PickupMinute closestPickupMinute = PickupMinute.ZERO;

        ListIterator<PickupMinute> iterator = VALUES.listIterator();

        while (iterator.hasNext()) {
            PickupMinute currentPickupMinute = iterator.next();
            int nextMinuteIndex = iterator.nextIndex();
            if (nextMinuteIndex > -1) {
                PickupMinute nextPickupMinute = VALUES.get(nextMinuteIndex);
                if (inRange(minute, currentPickupMinute.minute, nextPickupMinute.minute)) {
                    int firstHalf = minute - currentPickupMinute.minute;
                    int secondHalf = nextPickupMinute.minute - minute;

                    int compareHalves = Integer.compare(firstHalf, secondHalf);

                    closestPickupMinute = compareHalves >= 0 ? nextPickupMinute : currentPickupMinute;
                    break;
                }
            }
        }

        return closestPickupMinute;

    }

    private static boolean inRange(int value, int begin, int end) {
        return begin <= value && value <= end;
    }

    private int minute;

    PickupMinute(int minute) {
        this.minute = minute;
    }

    public int getValue() {
        return minute;
    }
}
