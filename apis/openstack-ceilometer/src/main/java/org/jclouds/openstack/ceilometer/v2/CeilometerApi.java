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
package org.jclouds.openstack.ceilometer.v2;

import com.google.inject.Provides;
import org.jclouds.location.Zone;
import org.jclouds.location.functions.RegionToEndpoint;
import org.jclouds.openstack.ceilometer.v2.features.AlarmApi;
import org.jclouds.openstack.ceilometer.v2.features.MeterApi;
import org.jclouds.openstack.ceilometer.v2.features.QueryApi;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.rest.annotations.EndpointParam;

import java.io.Closeable;
import java.util.Set;

/**
 * Provides access to the OpenStack Orchestration (Heat) API.
 *
 */
public interface CeilometerApi extends Closeable {

   @Provides
   @Zone
   Set<String> getConfiguredZones();

   /**
    * Provides access to Meters features.
    */
   @Delegate
   MeterApi getMeterApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

   /**
    * Provides access to Query features.
    */
   @Delegate
   QueryApi getQueryApi(@EndpointParam(parser = RegionToEndpoint.class) String region);

    /**
     * Provides access to Alarm features.
     */
    @Delegate
    AlarmApi getAlarmApi(@EndpointParam(parser = RegionToEndpoint.class) String region);


}
