package com.geomhwein.go.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geomhwein.go.command.comunityVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int comunityForm(comunityVO vo) {
		
		
		return userMapper.comunityForm(vo);
	}

	@Override
	public List<comunityVO> getComunityList() {
		
		
		return userMapper.getComunityList();
	}

	@Override
	public comunityVO getComunityDetail(int pst_ttl_no) {
		
		return userMapper.getComunityDetail(pst_ttl_no);
	}

	@Override
	public int comunityModifyForm(comunityVO vo) {
		
		return userMapper.comunityModifyForm(vo);
	}

	@Override
	public int comunityDelete(int pst_ttl_no) {
		// TODO Auto-generated method stub
		return userMapper.comunityDelete(pst_ttl_no);
	}



}
