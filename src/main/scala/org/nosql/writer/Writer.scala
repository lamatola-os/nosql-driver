package org.nosql.writer

import org.json.JSONObject

/**
  * Created by prayagupd
  * on 2/18/17.
  */

trait Writer {
  def write(json: JSONObject)
}
