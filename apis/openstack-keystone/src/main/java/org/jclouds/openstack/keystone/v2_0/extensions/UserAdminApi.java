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
package org.jclouds.openstack.keystone.v2_0.extensions;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptyPagedIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.EmptySetOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.keystone.v2_0.KeystoneFallbacks;
import org.jclouds.openstack.keystone.v2_0.domain.Role;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.keystone.v2_0.functions.internal.ParseUsers;
import org.jclouds.openstack.keystone.v2_0.options.CreateUserOptions;
import org.jclouds.openstack.keystone.v2_0.options.UpdateUserOptions;
import org.jclouds.openstack.v2_0.ServiceType;
import org.jclouds.openstack.v2_0.domain.PaginatedCollection;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.jclouds.openstack.v2_0.services.Extension;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.Transform;

import com.google.common.annotations.Beta;

import java.util.Set;

/**
 * Provides access to User Administration actions.
 */
@Beta
@Consumes(MediaType.APPLICATION_JSON)
@Extension(of = ServiceType.IDENTITY, namespace = ExtensionNamespaces.OS_KSADM,
      name = ExtensionNames.OS_KSADM, alias = ExtensionAliases.OS_KSADM)
@RequestFilters(AuthenticateRequest.class)
public interface UserAdminApi {

   /**
    * Creates a new user
    *
    * @return the new user
    */
   @Named("user:create")
   @POST
   @SelectJson("user")
   @Nullable
   @Path("/users")
   User create(@PayloadParam("name") String name, @PayloadParam("password") String password);

   /**
    * Creates a new user
    *
    * @return the new user
    */
   @Named("user:create")
   @POST
   @SelectJson("user")
   @MapBinder(CreateUserOptions.class)
   @Nullable
   @Path("/users")
   User create(@PayloadParam("name") String name,
         @PayloadParam("password") String password, CreateUserOptions options);

   /**
    * Deletes an user
    *
    * @return true if successful
    */
   @Named("user:delete")
   @DELETE
   @Path("/users/{id}")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String userId);

   /**
    * Updates an user
    *
    * @return the updated user
    */
   @Named("user:updateUser")
   @PUT
   @Path("/users/{id}")
   @SelectJson("user")
   @MapBinder(UpdateUserOptions.class)
   @Nullable
   User update(@PathParam("id") String id, UpdateUserOptions options);

   /**
    * Retrieve the list of users
    * <p/>
    * NOTE: this method is not in API documentation for keystone, but does work
    *
    * @return the list of users
    */
   @Named("user:list")
   @GET
   @Path("/users")
   @ResponseParser(ParseUsers.class)
   @Transform(ParseUsers.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<User> list();

   @Named("user:list")
   @GET
   @Path("/users")
   @ResponseParser(ParseUsers.class)
   @Fallback(KeystoneFallbacks.EmptyPaginatedCollectionOnNotFoundOr404.class)
   PaginatedCollection<User> list(PaginationOptions options);

   /**
    * Retrieve information about a user, by user ID
    *
    * @return the information about the user
    */
   @Named("user:get")
   @GET
   @SelectJson("user")
   @Path("/users/{userId}")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   User get(@PathParam("userId") String userId);

   /**
    * Retrieve information about a user, by user name
    * <p/>
    * NOTE: currently not working in openstack ( https://bugs.launchpad.net/keystone/+bug/956687 )
    *
    * @return the information about the user
    */
   @Named("user:getByName")
   @GET
   @SelectJson("user")
   @Path("/users")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   User getByName(@QueryParam("name") String userName);

   /**
    * Retrieves the list of global roles associated with a specific user (excludes tenant roles).
    * <p/>
    * NOTE: Broken in openstack ( https://bugs.launchpad.net/keystone/+bug/933565 )
    *
    * @return the set of Roles granted to the user
    */
   @Named("user:listRolesOfUser")
   @GET
   @SelectJson("roles")
   @Path("/users/{userId}/roles")
   @Fallback(EmptySetOnNotFoundOr404.class)
   Set<Role> listRolesOfUser(@PathParam("userId") String userId);

   /**
    * List the roles a user has been granted on a specific tenant
    *
    * @return the set of roles
    */
   @Named("user:listRolesOfUserOnTenant")
   @GET
   @SelectJson("roles")
   @Path("/tenants/{tenantId}/users/{userId}/roles")
   @Fallback(EmptySetOnNotFoundOr404.class)
   Set<Role> listRolesOfUserOnTenant(@PathParam("userId") String userId,
                                     @PathParam("tenantId") String tenantId);
}
