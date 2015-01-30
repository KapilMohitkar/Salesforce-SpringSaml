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

/**
 *
 * @author ruser2
 */
public class SalesforceIDConverter {
     public static String convertID(String id)
    {
        if(id.length() == 18) return id;

        String suffix = "";
        for(int i=0;i<3;i++){

            Integer flags = 0;

            for(int j=0;j<5;j++){
                String c = id.substring(i*5+j,i*5+j+1);

                if(c.compareTo("A")  >= 0 && c.compareTo("Z") <= 0){

                    flags += 1 << j;
                }
            }

            if (flags <= 25) {

                suffix += "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(flags,flags+1);

            }else suffix += "012345".substring(flags-26,flags-26+1);
        }

        return id+suffix;
    }
}
