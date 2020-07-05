/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.repository;

import edu.microservices.book.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author zyx
 * @since 2020/7/4
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);

}
