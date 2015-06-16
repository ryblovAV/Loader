package org.loader.reader

import grizzled.slf4j.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository("outReader")
class OutReader extends JdbcTemplatesUtl with Logging {
  @Autowired
  protected var jdbcTemplate:NamedParameterJdbcTemplate = _
}
