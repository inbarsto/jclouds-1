/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.glance.v1_0.options;


/**
 * Fields used in Glance options
 */
public enum ImageField {
   ID, NAME, CHECKSUM, MIN_DISK, MIN_RAM, IS_PUBLIC, PROTECTED, CREATED_AT, UPDATED_AT, DELETED_AT,
   OWNER, LOCATION, COPY_FROM, STATUS, DISK_FORMAT, CONTAINER_FORMAT, SIZE, SIZE_MIN, SIZE_MAX, STORE, PROPERTY;

   public static final String HEADER_PREFIX = "X-Image-Meta-";
   public static final String GLANCE_HEADER_PREFIX = "x-glance-api-";

   public String asParam() {
      return name().toLowerCase();
   }

   public String asGlanceHeader() {
      return GLANCE_HEADER_PREFIX + name().charAt(0) + name().substring(1).toLowerCase();
   }

   public String asHeader() {
      return HEADER_PREFIX + name().charAt(0) + name().substring(1).toLowerCase();
   }
}
