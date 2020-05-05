package ru.otus.graduation.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class HelperService {

    public String getBeginningOfLevel(@NotNull String level){
        if(!level.endsWith("0")) {
            return level;
        }else {
            int lastIndex = 0;
            char array[] = level.toCharArray();
            for(int i = level.length()-1; i >= 0; i--)
                if (array[i] != '0') {
                    lastIndex = i+1;
                    break;
                }
            return level.substring(0, lastIndex);
        }
    }
}
