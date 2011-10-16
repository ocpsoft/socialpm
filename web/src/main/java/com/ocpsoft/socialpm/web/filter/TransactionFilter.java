/*
 * Copyright 2011 <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ocpsoft.socialpm.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.ocpsoft.logging.Logger;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@WebFilter(urlPatterns = "/*", dispatcherTypes = { DispatcherType.REQUEST })
public class TransactionFilter implements Filter
{
   Logger log = Logger.getLogger(TransactionFilter.class);

   @Inject
   private UserTransaction tx;

   @Override
   public void init(FilterConfig filterConfig) throws ServletException
   {}

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException
   {
      try {
         if (!(Status.STATUS_NO_TRANSACTION == tx.getStatus()))
         {
            log.warn("Transaction was still associated with request.");
            tx.rollback();
         }
      }
      catch (SystemException e) {
         log.error("Transaction was still associated with request.", e);
      }

      chain.doFilter(request, response);
   }

   @Override
   public void destroy()
   {}

}
