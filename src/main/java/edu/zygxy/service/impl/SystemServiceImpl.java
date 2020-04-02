package edu.zygxy.service.impl;

import edu.zygxy.dao.SystemMapper;
import edu.zygxy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public void initialization() {
        systemMapper.initialization();
        systemMapper.deleteDepartment();
        systemMapper.deleteHoliday();
        systemMapper.deleteSchedule();
        systemMapper.deleteUser();
        systemMapper.deleteWorkCheck();

    }

    @Override
    public void backup() {

    }
}
