package org.nosql.reader

import java.util

import org.json.JSONObject

/**
  * Created by prayagupd
  * on 2/18/17.
  */

trait Reader {
  def read(query: util.LinkedHashMap[String, String], rangeKeys: util.LinkedHashMap[String, String]) : Option[JSONObject]
}
