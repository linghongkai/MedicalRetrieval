package com.medicalretrieval.service;

import com.medicalretrieval.pojo.user.User;
import org.jetbrains.annotations.NotNull;
import org.python.antlr.ast.List;
import org.python.antlr.ast.Str;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.permissionGroupId = ?1 where u.id = ?2")
    int updatePermissionGroupIdById(int permissionGroupId, long id);
    @Transactional
    @Modifying
    @Query("update User u set u.disabled = ?1 where u.id = ?2")
    int updateDisabledById(int disabled, long id);
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1, u.email = ?2, u.telephone = ?3, u.permissionGroupId = ?4, u.avatar = ?5, u.disabled = ?6 " +
            "where u.id = ?7")
    int updatePasswordAndEmailAndTelephoneAndPermissionGroupIdAndAvatarAndDisabledById(String password, String email, String telephone, int permissionGroupId, String avatar, int disabled, long id);

    void deleteById(@NotNull Long id);

    User findByAccountAndPassword(String account,String password);


}
