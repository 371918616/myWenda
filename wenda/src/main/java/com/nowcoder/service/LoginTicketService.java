package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/10/6.
 */
@Service
public class LoginTicketService {

    @Autowired
    LoginTicketDAO loginTicketDAO;
}
