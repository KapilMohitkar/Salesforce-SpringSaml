/*
 * Copyright 2015 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.relecotech.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author ruser2
 */
public class AddSubtractTime {

    public static String getAddedTime(String myTime,TimeZone timeZone ) throws ParseException {
        //String myTime = "14:10";
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
        Date d = df.parse(myTime);
      // TimeZone timeZone = TimeZone.getTimeZone(timeZoneSting);
//        Calendar now = Calendar.getInstance(timeZone);
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 15);
        String newTime = df.format(cal.getTime());
        return newTime;
    }
    public static String getSubtractedTime(String myTime,TimeZone timeZone ) throws ParseException {
       //String myTime = "14:10";
         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
        Date d = df.parse(myTime);
      // TimeZone timeZone = TimeZone.getTimeZone(timeZoneSting);
//        Calendar now = Calendar.getInstance(timeZone);
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(d);
        cal.add(Calendar.MINUTE, -15);
        String newTime = df.format(cal.getTime());
        return newTime;
    }

}
