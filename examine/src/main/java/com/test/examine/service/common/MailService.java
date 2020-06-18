package com.test.examine.service.common;





public interface MailService {
    //激活账号发送信息
    void sendMail(String toMail);

    //学生给管理员发送信息
    void sendException(String info,String receiver);
}
