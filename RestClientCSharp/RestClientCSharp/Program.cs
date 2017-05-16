using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace RestClientCSharp
{
    class Program
    {
        static HttpClient client = new HttpClient();

        static void Main(string[] args)
        {
            RunAsync().Wait();

            Console.ReadKey();
        }

        static async Task RunAsync()
        {
            string baseAdr = "http://localhost:8080/festival/artists";
            client.BaseAddress = new Uri(baseAdr);
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
            var result = GetArtistAsync(baseAdr + "/10");
            
            var resultArr = GetAllArtists(baseAdr);

            await Task.WhenAll(result, resultArr);

            Console.WriteLine();
            Console.WriteLine("Add");
            Artist add = await AddArtist();
            await GetAllArtists(baseAdr);

            Console.WriteLine();
            Console.WriteLine("Update");
            await UpdateArtist(add.IdArtist);
            await GetAllArtists(baseAdr);

            Console.WriteLine();
            Console.WriteLine("Delete");
            await RemoveArtist(add.IdArtist);
            await GetAllArtists(baseAdr);
        }

        static async Task<Artist[]> GetAllArtists(string path)
        {
            Artist[] artists = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                artists = await response.Content.ReadAsAsync<Artist[]>();
            }
            foreach (Artist artist in artists)
            {
                Console.WriteLine("Get all" + artist);
            }
            return artists;
        }

        static async Task<Artist> GetArtistAsync(string path)
        {
            Artist artist = null;
            HttpResponseMessage httpResponse = await client.GetAsync(path);
            if (httpResponse.IsSuccessStatusCode)
            {
                artist = await httpResponse.Content.ReadAsAsync<Artist>();
            }
            Console.WriteLine("Get by id " + artist);
            return artist;
        }

        static async Task<Artist> AddArtist()
        {
            Artist artist = null;

            string baseAdr = "http://localhost:8080/festival/artists";

            string data = @"{ ""name"" : ""testCs""}";
            var response = await client.PostAsync(baseAdr, new StringContent(data, Encoding.UTF8, "application/json"));

            artist = await response.Content.ReadAsAsync<Artist>();

            Console.WriteLine("Added " + artist);

            return artist;
        }

        static async Task RemoveArtist(int id)
        {
            string baseAdr = "http://localhost:8080/festival/artists";

            var response = await client.DeleteAsync(baseAdr + "/" + id);

            Console.WriteLine("delete artist with id : " + id);

        }

        static async Task UpdateArtist(int id)
        {
            Artist artist = null;

            string baseAdr = "http://localhost:8080/festival/artists/" + id;

            string data = @"{ ""name"" : ""testCsUpdated""}";
            var response = await client.PutAsync(baseAdr, new StringContent(data, Encoding.UTF8, "application/json"));

            artist = await response.Content.ReadAsAsync<Artist>();

            Console.WriteLine("Updated " + artist);
        }
    }

    public class Artist
    {
        private int idArtist;
        private string name;

        public Artist() { }

        public Artist(string name)
        {
            this.name = name;
        }

        public Artist(int idArtist, string name)
        {
            this.idArtist = idArtist;
            this.name = name;
        }

        public int IdArtist
        {
            get
            {
                return idArtist;
            }

            set
            {
                idArtist = value;
            }
        }

        public string Name
        {
            get
            {
                return name;
            }

            set
            {
                name = value;
            }
        }

        public override string ToString()
        {
            return idArtist + " " + name;
        }
    }
}
