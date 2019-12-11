package com.sifo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ResultData<T> {
    private String code;
    private String msg;
    private T data;

    public interface ResultCodeList{
        String OK="200";
        String ERROR="500";
    }
}
