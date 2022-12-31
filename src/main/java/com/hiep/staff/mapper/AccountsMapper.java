package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.AccountsEntity;
import com.hiep.staff.model.AccountsModel;
import com.hiep.staff.model.DeleteModel;

@Mapper
public interface AccountsMapper {

	int registerUser(AccountsModel accountsModel);

	List<AccountsEntity> getAccounts();

	List<AccountsEntity> getAccount(int id);

	List<AccountsEntity> searchAccount(int value);

	int updateAccount(AccountsModel accountsModel);

	int deleteAccount(DeleteModel deleteModel);

	List<AccountsEntity> searchAccountByStaffId(int id);

	int checkUser(AccountsModel accountsModel);

	int checkStaffId(AccountsModel accountsModel);

	List<AccountsEntity> login(AccountsModel accountsModel);

	void updateUser(AccountsModel accountsModel);

	void updatePassword(AccountsModel accountsModel);
}
