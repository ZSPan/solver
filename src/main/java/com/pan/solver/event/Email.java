package com.pan.solver.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yemingfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String to;
    private String subject;
    private String text;
}