using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cw5.Middlewares
{
    public class MiddlewareLogin
    {
        private readonly RequestDelegate next;
        public MiddlewareLogin(RequestDelegate next)
        {
            this.next = next;
        }
        public async Task InvokeAsync(HttpContext httpContext)
        {
            httpContext.Request.EnableBuffering();

            if (httpContext.Request != null)
            {
                string path = httpContext.Request.Path;
                string query = httpContext.Request?.query.ToString();
                string method = httpContext.Request.Method.ToString();
                string bodyString = "";

                using (StreamReader reader
                 = new StreamReader(httpContext.Request.Body, Encoding.UTF8, true, 1024, true))
                {
                    bodyString = await reader.ReadToEndAsync();
                }

                File.AppendAllText("log.log", path + query + method + bodyString);
            }
            await next(httpContext);
        }
    }
}
