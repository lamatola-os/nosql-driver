package org.nosql.reader

import org.json.JSONObject

/**
  * Created by prayagupd
  * on 2/18/17.
  */

trait Reader {
  def read(query: JSONObject) : JSONObject
}
