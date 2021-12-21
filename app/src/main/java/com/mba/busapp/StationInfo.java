package com.mba.busapp;

import com.naver.maps.geometry.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 정류장 정보, 경로 관리
 */
public class StationInfo {
    private static StationInfo instance;

    // 정류장 정보
    private final static HashMap<String, LatLng> stationInfo = new HashMap<String, LatLng>() {{
        put("명지대", new LatLng(37.224283500000006, 127.18728609999998));
        put("상공회의소", new LatLng(37.230680400000004, 127.1882456));
        put("진입로", new LatLng(37.23399210000001,  127.18882909999999));
        put("명지대역", new LatLng(37.238513300000015, 127.18960559999998));
        put("진입로(명지대방향)", new LatLng(37.233999900000015, 127.18861349999999));
        put("이마트", new LatLng(37.23036920601031 , 127.18799722805205));
        put("명진당", new LatLng(37.22218358841614, 127.18895343450612));
        put("제3공학관", new LatLng(37.219509212602546, 127.1829915220452));
        put("동부경찰서", new LatLng(37.23475516860965 , 127.19817660622552));
        put("용인시장", new LatLng(37.235430174474516, 127.20667763142193));
        put("중앙공영주차장", new LatLng(37.23391585619981 , 127.20892718244508));
        put("제1공학관", new LatLng(37.22271140883418, 127.18678412115244));
    }};

    // 이전 정류장 ~ string 까지의 경로
    private final static HashMap<String, ArrayList<LatLng>> routeInfo = new HashMap<>();

    private StationInfo() {
        ArrayList<LatLng> routes = new ArrayList<>();

        // Fake route
        routes.add(new LatLng(37.2242590,127.1872767));
        routeInfo.put("명지대", routes);

        // 명지대 ~ 상공회의소 경로
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2242590,127.1872767));
        routes.add(new LatLng(37.2242341,127.1873974));
        routes.add(new LatLng(37.2242208,127.1874831));
        routes.add(new LatLng(37.2242236,127.1875428));
        routes.add(new LatLng(37.2242653,127.1876294));
        routes.add(new LatLng(37.2243034,127.1877093));
        routes.add(new LatLng(37.2243116,127.1877532));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2249400,127.1878014));
        routes.add(new LatLng(37.2249446,127.1878217));
        routes.add(new LatLng(37.2249545,127.1878385));
        routes.add(new LatLng(37.2249681,127.1878497));
        routes.add(new LatLng(37.2249825,127.1878564));
        routes.add(new LatLng(37.2250006,127.1878564));
        routes.add(new LatLng(37.2250141,127.1878507));
        routes.add(new LatLng(37.2250267,127.1878405));
        routes.add(new LatLng(37.2250383,127.1878213));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2290126,127.1878173));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2304322,127.1880955));
        routes.add(new LatLng(37.2305674,127.1881254));
        routes.add(new LatLng(37.2306793,127.1881464));
        routes.add(new LatLng(37.2306919,127.1881486));
        routeInfo.put("상공회의소", routes);

        // 상공회의소 ~ 진입로
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2306919,127.1881486));
        routes.add(new LatLng(37.2307884,127.1881684));
        routes.add(new LatLng(37.2310247,127.1882114));
        routes.add(new LatLng(37.2311203,127.1882313));
        routes.add(new LatLng(37.2311906,127.1882457));
        routes.add(new LatLng(37.2312628,127.1882612));
        routes.add(new LatLng(37.2314152,127.1882944));
        routes.add(new LatLng(37.2316614,127.1883452));
        routes.add(new LatLng(37.2318373,127.1883772));
        routes.add(new LatLng(37.2319726,127.1884026));
        routes.add(new LatLng(37.2321087,127.1884268));
        routes.add(new LatLng(37.2322494,127.1884522));
        routes.add(new LatLng(37.2324189,127.1884774));
        routes.add(new LatLng(37.2325650,127.1884960));
        routes.add(new LatLng(37.2327409,127.1885189));
        routes.add(new LatLng(37.2328887,127.1885352));
        routes.add(new LatLng(37.2329915,127.1885494));
        routes.add(new LatLng(37.2335768,127.1886361));
        routes.add(new LatLng(37.2336309,127.1886449));
        routes.add(new LatLng(37.2336516,127.1886482));
        routes.add(new LatLng(37.2337481,127.1886647));
        routes.add(new LatLng(37.2340033,127.1887008));
        routeInfo.put("진입로", routes);

        // 진입로 ~ 명지대역
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2340033,127.1887008));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2362014,127.1892228));
        routes.add(new LatLng(37.2362402,127.1892351));
        routes.add(new LatLng(37.2362664,127.1892440));
        routes.add(new LatLng(37.2368915,127.1894387));
        routes.add(new LatLng(37.2370368,127.1894843));
        routes.add(new LatLng(37.2371243,127.1895121));
        routes.add(new LatLng(37.2371847,127.1895311));
        routes.add(new LatLng(37.2373065,127.1895700));
        routes.add(new LatLng(37.2377368,127.1897058));
        routes.add(new LatLng(37.2378677,127.1897470));
        routes.add(new LatLng(37.2379498,127.1897748));
        routes.add(new LatLng(37.2382339,127.1898526));
        routes.add(new LatLng(37.2382465,127.1898548));
        routes.add(new LatLng(37.2383169,127.1898748));
        routes.add(new LatLng(37.2383554,127.1897720));
        routes.add(new LatLng(37.2383679,127.1897359));
        routes.add(new LatLng(37.2384125,127.1895632));
        routeInfo.put("명지대역", routes);

        // 명지대역 ~ 진입로(명지대방향)
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2384125,127.1895632));
        routes.add(new LatLng(37.2384911,127.1892585));
        routes.add(new LatLng(37.2385426,127.1889697));
        routes.add(new LatLng(37.2385531,127.1888591));
        routes.add(new LatLng(37.2385749,127.1885738));
        routes.add(new LatLng(37.2386561,127.1875870));
        routes.add(new LatLng(37.2386993,127.1865451));
        routes.add(new LatLng(37.2387124,127.1863759));
        routes.add(new LatLng(37.2387245,127.1861864));
        routes.add(new LatLng(37.2386595,127.1861461));
        routes.add(new LatLng(37.2384329,127.1860039));
        routes.add(new LatLng(37.2379833,127.1857374));
        routes.add(new LatLng(37.2376700,127.1855628));
        routes.add(new LatLng(37.2374832,127.1854610));
        routes.add(new LatLng(37.2373306,127.1853624));
        routes.add(new LatLng(37.2369839,127.1851621));
        routes.add(new LatLng(37.2369500,127.1853110));
        routes.add(new LatLng(37.2369082,127.1854995));
        routes.add(new LatLng(37.2368984,127.1855446));
        routes.add(new LatLng(37.2368832,127.1856123));
        routes.add(new LatLng(37.2368290,127.1858775));
        routes.add(new LatLng(37.2365664,127.1867963));
        routes.add(new LatLng(37.2363098,127.1879450));
        routes.add(new LatLng(37.2362929,127.1880297));
        routes.add(new LatLng(37.2361390,127.1887890));
        routes.add(new LatLng(37.2361069,127.1889492));
        routes.add(new LatLng(37.2360758,127.1891016));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2339916,127.1886998));
        routeInfo.put("진입로(명지대방향)", routes);

        // 진입로(명지대방향) ~ 이마트
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2339916,127.1886998));
        routes.add(new LatLng(37.2337481,127.1886647));
        routes.add(new LatLng(37.2336516,127.1886482));
        routes.add(new LatLng(37.2336309,127.1886449));
        routes.add(new LatLng(37.2335768,127.1886361));
        routes.add(new LatLng(37.2329915,127.1885494));
        routes.add(new LatLng(37.2328887,127.1885352));
        routes.add(new LatLng(37.2327409,127.1885189));
        routes.add(new LatLng(37.2325650,127.1884960));
        routes.add(new LatLng(37.2324189,127.1884774));
        routes.add(new LatLng(37.2322494,127.1884522));
        routes.add(new LatLng(37.2320213,127.1884114));
        routes.add(new LatLng(37.2319726,127.1884026));
        routes.add(new LatLng(37.2316614,127.1883452));
        routes.add(new LatLng(37.2314152,127.1882944));
        routes.add(new LatLng(37.2312628,127.1882612));
        routes.add(new LatLng(37.2311906,127.1882457));
        routes.add(new LatLng(37.2311203,127.1882313));
        routes.add(new LatLng(37.2310247,127.1882114));
        routes.add(new LatLng(37.2307884,127.1881684));
        routes.add(new LatLng(37.2306793,127.1881464));
        routes.add(new LatLng(37.2305674,127.1881254));
        routes.add(new LatLng(37.2304322,127.1880955));
        routes.add(new LatLng(37.2303582,127.1880812));
        routeInfo.put("이마트", routes);

        // 이마트 ~ 명진당
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2303582,127.1880812));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2266912,127.1877220));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2250428,127.1877953));
        routes.add(new LatLng(37.2250418,127.1877773));
        routes.add(new LatLng(37.2250355,127.1877570));
        routes.add(new LatLng(37.2250273,127.1877424));
        routes.add(new LatLng(37.2250156,127.1877312));
        routes.add(new LatLng(37.2250020,127.1877245));
        routes.add(new LatLng(37.2249867,127.1877234));
        routes.add(new LatLng(37.2249741,127.1877246));
        routes.add(new LatLng(37.2249678,127.1877280));
        routes.add(new LatLng(37.2249624,127.1877303));
        routes.add(new LatLng(37.2249498,127.1877461));
        routes.add(new LatLng(37.2249399,127.1877743));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2241642,127.1879241));
        routes.add(new LatLng(37.2237579,127.1880182));
        routes.add(new LatLng(37.2235453,127.1880664));
        routes.add(new LatLng(37.2234291,127.1880849));
        routes.add(new LatLng(37.2233155,127.1880899));
        routes.add(new LatLng(37.2232049,127.1881895));
        routes.add(new LatLng(37.2226888,127.1886572));
        routes.add(new LatLng(37.2226357,127.1887059));
        routes.add(new LatLng(37.2222445,127.1890580));
        routeInfo.put("명진당", routes);

        // 명진당 ~ 제3공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2222445,127.1890580));
        routes.add(new LatLng(37.2220829, 127.1891526));
        routes.add(new LatLng(37.2212713, 127.1875192));
        routes.add(new LatLng(37.2210687,127.1868469));
        routes.add(new LatLng(37.2210497,127.1868244));
        routes.add(new LatLng(37.2209666,127.1867864));
        routes.add(new LatLng(37.2208909,127.1867743));
        routes.add(new LatLng(37.2207412,127.1867659));
        routes.add(new LatLng(37.2206583,127.1867618));
        routes.add(new LatLng(37.2205303,127.1867623));
        routes.add(new LatLng(37.2205078,127.1867624));
        routes.add(new LatLng(37.2203661,127.1867122));
        routes.add(new LatLng(37.2202414,127.1865989));
        routes.add(new LatLng(37.2201582,127.1864989));
        routes.add(new LatLng(37.2200749,127.1863370));
        routes.add(new LatLng(37.2199200,127.1857042));
        routes.add(new LatLng(37.2198799,127.1855409));
        routes.add(new LatLng(37.2198415,127.1853517));
        routes.add(new LatLng(37.2197575,127.1849373));
        routes.add(new LatLng(37.2196493,127.1842490));
        routes.add(new LatLng(37.2196184,127.1841511));
        routes.add(new LatLng(37.2195138,127.1838280));
        routes.add(new LatLng(37.2194862,127.1836185));
        routes.add(new LatLng(37.2194853,127.1836084));
        routes.add(new LatLng(37.2194667,127.1833988));
        routes.add(new LatLng(37.2194663,127.1832489));
        routes.add(new LatLng(37.2195019,127.1830661));
        routes.add(new LatLng(37.2195206,127.1829950));
        routeInfo.put("제3공학관", routes);

        // 진입로 ~ 동부 경찰서
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2340033,127.1887008));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2360615,127.1891715));
        routes.add(new LatLng(37.2360144,127.1894040));
        routes.add(new LatLng(37.2359823,127.1895631));
        routes.add(new LatLng(37.2357697,127.1906146));
        routes.add(new LatLng(37.2354982,127.1919551));
        routes.add(new LatLng(37.2354822,127.1920397));
        routes.add(new LatLng(37.2354564,127.1921773));
        routes.add(new LatLng(37.2354075,127.1924301));
        routes.add(new LatLng(37.2351789,127.1936181));
        routes.add(new LatLng(37.2351576,127.1937490));
        routes.add(new LatLng(37.2351337,127.1938833));
        routes.add(new LatLng(37.2350586,127.1944743));
        routes.add(new LatLng(37.2350380,127.1945601));
        routes.add(new LatLng(37.2350095,127.1946831));
        routes.add(new LatLng(37.2349408,127.1949494));
        routes.add(new LatLng(37.2348577,127.1952248));
        routes.add(new LatLng(37.2348165,127.1953242));
        routes.add(new LatLng(37.2347493,127.1954868));
        routes.add(new LatLng(37.2346561,127.1957239));
        routes.add(new LatLng(37.2346266,127.1957996));
        routes.add(new LatLng(37.2347043,127.1958759));
        routes.add(new LatLng(37.2347487,127.1959468));
        routes.add(new LatLng(37.2347705,127.1960154));
        routes.add(new LatLng(37.2347869,127.1960887));
        routes.add(new LatLng(37.2347988,127.1961698));
        routes.add(new LatLng(37.2348144,127.1962858));
        routes.add(new LatLng(37.2348172,127.1963106));
        routes.add(new LatLng(37.2348200,127.1963467));
        routes.add(new LatLng(37.2348211,127.1964188));
        routes.add(new LatLng(37.2348163,127.1966624));
        routes.add(new LatLng(37.2348357,127.1975597));
        routes.add(new LatLng(37.2348380,127.1977446));
        routes.add(new LatLng(37.2348395,127.1979689));
        routes.add(new LatLng(37.2348436,127.1981718));
        routeInfo.put("동부경찰서", routes);

        // 동부경찰서 ~ 용인 시장
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2348436,127.1981718));
        routes.add(new LatLng(37.2348498,127.1984999));
        routes.add(new LatLng(37.2348565,127.1989970));
        routes.add(new LatLng(37.2348577,127.1991120));
        routes.add(new LatLng(37.2348660,127.1999124));
        routes.add(new LatLng(37.2348672,127.2000217));
        routes.add(new LatLng(37.2348751,127.2006440));
        routes.add(new LatLng(37.2348772,127.2007534));
        routes.add(new LatLng(37.2348882,127.2015335));
        routes.add(new LatLng(37.2348875,127.2016462));
        routes.add(new LatLng(37.2348993,127.2023620));
        routes.add(new LatLng(37.2349032,127.2025007));
        routes.add(new LatLng(37.2349187,127.2032932));
        routes.add(new LatLng(37.2349577,127.2037372));
        routes.add(new LatLng(37.2350088,127.2039895));
        routes.add(new LatLng(37.2350289,127.2040898));
        routes.add(new LatLng(37.2350499,127.2041991));
        routes.add(new LatLng(37.2351714,127.2048434));
        routes.add(new LatLng(37.2352024,127.2050068));
        routes.add(new LatLng(37.2353220,127.2056061));
        routes.add(new LatLng(37.2353366,127.2056782));
        routes.add(new LatLng(37.2353458,127.2057266));
        routes.add(new LatLng(37.2353531,127.2057638));
        routes.add(new LatLng(37.2353658,127.2058246));
        routes.add(new LatLng(37.2354334,127.2061806));
        routes.add(new LatLng(37.2355047,127.2065535));
        routes.add(new LatLng(37.2355184,127.2066583));
        routeInfo.put("용인시장", routes);

        //용인시장 ~ 중앙공영주차장
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2355184,127.2066583));
        routes.add(new LatLng(37.2355910,127.2072240));
        routes.add(new LatLng(37.2356003,127.2073367));
        routes.add(new LatLng(37.2356003,127.2073412));
        routes.add(new LatLng(37.2356172,127.2076106));
        routes.add(new LatLng(37.2355902,127.2079613));
        routes.add(new LatLng(37.2355823,127.2080583));
        routes.add(new LatLng(37.2355667,127.2083244));
        routes.add(new LatLng(37.2355557,127.2085984));
        routes.add(new LatLng(37.2355549,127.2086423));
        routes.add(new LatLng(37.2355472,127.2088070));
        routes.add(new LatLng(37.2353597,127.2088336));
        routes.add(new LatLng(37.2352669,127.2088407));
        routes.add(new LatLng(37.2347956,127.2088967));
        routes.add(new LatLng(37.2346785,127.2089084));
        routes.add(new LatLng(37.2345199,127.2089338));
        routes.add(new LatLng(37.2344199,127.2089477));
        routes.add(new LatLng(37.2339179,127.2089891));
        routeInfo.put("중앙공영주차장", routes);

        // 중앙공영주차장 ~ 진입로(명지대방향)
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2339179,127.2089891));
        routes.add(new LatLng(37.2334961,127.2090245));
        routes.add(new LatLng(37.2334051,127.2090350));
        routes.add(new LatLng(37.2333826,127.2090385));
        routes.add(new LatLng(37.2333240,127.2090489));
        routes.add(new LatLng(37.2332528,127.2090582));
        routes.add(new LatLng(37.2332425,127.2088970));
        routes.add(new LatLng(37.2332369,127.2088136));
        routes.add(new LatLng(37.2332106,127.2083774));
        routes.add(new LatLng(37.2331815,127.2079052));
        routes.add(new LatLng(37.2331749,127.2077992));
        routes.add(new LatLng(37.2330968,127.2064625));
        routes.add(new LatLng(37.2330816,127.2061424));
        routes.add(new LatLng(37.2330122,127.2046940));
        routes.add(new LatLng(37.2329034,127.2044780));
        routes.add(new LatLng(37.2328910,127.2041872));
        routes.add(new LatLng(37.2328898,127.2040745));
        routes.add(new LatLng(37.2328776,127.2035232));
        routes.add(new LatLng(37.2328790,127.2033609));
        routes.add(new LatLng(37.2328788,127.2032741));
        routes.add(new LatLng(37.2328800,127.2030430));
        routes.add(new LatLng(37.2328832,127.2024996));
        routes.add(new LatLng(37.2328829,127.2023993));
        routes.add(new LatLng(37.2328844,127.2022865));
        routes.add(new LatLng(37.2328875,127.2013373));
        routes.add(new LatLng(37.2328912,127.2010239));
        routes.add(new LatLng(37.2328867,127.2006801));
        routes.add(new LatLng(37.2328855,127.2005437));
        routes.add(new LatLng(37.2328831,127.2003374));
        routes.add(new LatLng(37.2328856,127.2002393));
        routes.add(new LatLng(37.2328881,127.2001637));
        routes.add(new LatLng(37.2328969,127.2000758));
        routes.add(new LatLng(37.2329040,127.2000205));
        routes.add(new LatLng(37.2329243,127.1998750));
        routes.add(new LatLng(37.2329660,127.1995997));
        routes.add(new LatLng(37.2332329,127.1989121));
        routes.add(new LatLng(37.2332741,127.1988116));
        routes.add(new LatLng(37.2334039,127.1984842));
        routes.add(new LatLng(37.2334075,127.1984740));
        routes.add(new LatLng(37.2335446,127.1981590));
        routes.add(new LatLng(37.2338995,127.1973368));
        routes.add(new LatLng(37.2340384,127.1970319));
        routes.add(new LatLng(37.2343324,127.1963870));
        routes.add(new LatLng(37.2344490,127.1961486));
        routes.add(new LatLng(37.2345100,127.1960289));
        routes.add(new LatLng(37.2345620,127.1959272));
        routes.add(new LatLng(37.2346051,127.1958425));
        routes.add(new LatLng(37.2346266,127.1957996));
        routes.add(new LatLng(37.2347493,127.1954868));
        routes.add(new LatLng(37.2348165,127.1953242));
        routes.add(new LatLng(37.2348577,127.1952248));
        routes.add(new LatLng(37.2349408,127.1949494));
        routes.add(new LatLng(37.2350095,127.1946831));
        routes.add(new LatLng(37.2350380,127.1945601));
        routes.add(new LatLng(37.2350586,127.1944743));
        routes.add(new LatLng(37.2351570,127.1942022));
        routes.add(new LatLng(37.2352338,127.1939178));
        routes.add(new LatLng(37.2352614,127.1937801));
        routes.add(new LatLng(37.2352881,127.1936459));
        routes.add(new LatLng(37.2354678,127.1927511));
        routes.add(new LatLng(37.2355275,127.1924533));
        routes.add(new LatLng(37.2355746,127.1922175));
        routes.add(new LatLng(37.2356040,127.1920764));
        routes.add(new LatLng(37.2356663,127.1917706));
        routes.add(new LatLng(37.2357455,127.1913746));
        routes.add(new LatLng(37.2358684,127.1907856));
        routes.add(new LatLng(37.2361204,127.1896211));
        routes.add(new LatLng(37.2361542,127.1894519));
        routes.add(new LatLng(37.2361871,127.1892894));
        routes.add(new LatLng(37.2360246,127.1891581));
        routes.add(new LatLng(37.2359984,127.1891481));
        routes.add(new LatLng(37.2357999,127.1890745));
        routes.add(new LatLng(37.2357061,127.1890456));
        routes.add(new LatLng(37.2352324,127.1888999));
        routes.add(new LatLng(37.2347968,127.1888182));
        routes.add(new LatLng(37.2346435,127.1887929));
        routes.add(new LatLng(37.2344659,127.1887655));
        routes.add(new LatLng(37.2342179,127.1887315));
        routes.add(new LatLng(37.2339916,127.1886998));
        routeInfo.put("진입로(명지대방향)_시내", routes);

        // 이마트 ~ 제1공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2303582,127.1880812));
        routes.add(new LatLng(37.2296124,127.1879366));
        routes.add(new LatLng(37.2291984,127.1878548));
        routes.add(new LatLng(37.2290658,127.1878272));
        routes.add(new LatLng(37.2284102,127.1877048));
        routes.add(new LatLng(37.2283831,127.1876993));
        routes.add(new LatLng(37.2281640,127.1876573));
        routes.add(new LatLng(37.2279647,127.1876198));
        routes.add(new LatLng(37.2279250,127.1876121));
        routes.add(new LatLng(37.2277158,127.1875882));
        routes.add(new LatLng(37.2277077,127.1875871));
        routes.add(new LatLng(37.2275085,127.1875913));
        routes.add(new LatLng(37.2271750,127.1875994));
        routes.add(new LatLng(37.2266912,127.1877220));
        routes.add(new LatLng(37.2266831,127.1877243));
        routes.add(new LatLng(37.2263498,127.1877990));
        routes.add(new LatLng(37.2262246,127.1878367));
        routes.add(new LatLng(37.2258247,127.1879623));
        routes.add(new LatLng(37.2257184,127.1879876));
        routes.add(new LatLng(37.2257040,127.1879899));
        routes.add(new LatLng(37.2255995,127.1879959));
        routes.add(new LatLng(37.2255003,127.1879986));
        routes.add(new LatLng(37.2253704,127.1879642));
        routes.add(new LatLng(37.2252666,127.1879161));
        routes.add(new LatLng(37.2252513,127.1879083));
        routes.add(new LatLng(37.2251556,127.1878625));
        routes.add(new LatLng(37.2251547,127.1878625));
        routes.add(new LatLng(37.2251195,127.1878457));
        routes.add(new LatLng(37.2250401,127.1878100));
        routes.add(new LatLng(37.2250428,127.1877953));
        routes.add(new LatLng(37.2250418,127.1877773));
        routes.add(new LatLng(37.2250355,127.1877570));
        routes.add(new LatLng(37.2250273,127.1877424));
        routes.add(new LatLng(37.2250156,127.1877312));
        routes.add(new LatLng(37.2250020,127.1877245));
        routes.add(new LatLng(37.2249867,127.1877234));
        routes.add(new LatLng(37.2249741,127.1877246));
        routes.add(new LatLng(37.2249678,127.1877280));
        routes.add(new LatLng(37.2249624,127.1877303));
        routes.add(new LatLng(37.2249498,127.1877461));
        routes.add(new LatLng(37.2249399,127.1877743));
        routes.add(new LatLng(37.2249382,127.1877901));
        routes.add(new LatLng(37.2248940,127.1877903));
        routes.add(new LatLng(37.2248237,127.1877917));
        routes.add(new LatLng(37.2247813,127.1877919));
        routes.add(new LatLng(37.2246984,127.1878013));
        routes.add(new LatLng(37.2246930,127.1878024));
        routes.add(new LatLng(37.2246345,127.1878128));
        routes.add(new LatLng(37.2242795,127.1878977));
        routes.add(new LatLng(37.2241642,127.1879241));
        routes.add(new LatLng(37.2237579,127.1880182));
        routes.add(new LatLng(37.2235453,127.1880664));
        routes.add(new LatLng(37.2234291,127.1880849));
        routes.add(new LatLng(37.2233155,127.1880899));
        routes.add(new LatLng(37.2232666,127.1879853));
        routes.add(new LatLng(37.2231134,127.1876567));
        routes.add(new LatLng(37.2230174,127.1874790));
        routes.add(new LatLng(37.2226740,127.1868132));
        routeInfo.put("제1공학관", routes);

        // 제1공학관 ~ 제3공학관
        routes = new ArrayList<>();
        routes.add(new LatLng(37.2226740,127.1868132));
        routes.add(new LatLng(37.2226278,127.1867232));
        routes.add(new LatLng(37.2225081,127.1864622));
        routes.add(new LatLng(37.2223919,127.1864807));
        routes.add(new LatLng(37.2221580,127.1866845));
        routes.add(new LatLng(37.2218861,127.1864366));
        routes.add(new LatLng(37.2218103,127.1864042));
        routes.add(new LatLng(37.2217491,127.1864416));
        routes.add(new LatLng(37.2215492,127.1865495));
        routes.add(new LatLng(37.2213701,127.1866461));
        routes.add(new LatLng(37.2212576,127.1867243));
        routes.add(new LatLng(37.2211748,127.1867743));
        routes.add(new LatLng(37.2210759,127.1868558));
        routes.add(new LatLng(37.2210497,127.1868244));
        routes.add(new LatLng(37.2209666,127.1867864));
        routes.add(new LatLng(37.2208909,127.1867743));
        routes.add(new LatLng(37.2207412,127.1867659));
        routes.add(new LatLng(37.2206583,127.1867618));
        routes.add(new LatLng(37.2205303,127.1867623));
        routes.add(new LatLng(37.2205078,127.1867624));
        routes.add(new LatLng(37.2203661,127.1867122));
        routes.add(new LatLng(37.2202414,127.1865989));
        routes.add(new LatLng(37.2201582,127.1864989));
        routes.add(new LatLng(37.2200749,127.1863370));
        routes.add(new LatLng(37.2199200,127.1857042));
        routes.add(new LatLng(37.2198799,127.1855409));
        routes.add(new LatLng(37.2198415,127.1853517));
        routes.add(new LatLng(37.2197575,127.1849373));
        routes.add(new LatLng(37.2196493,127.1842490));
        routes.add(new LatLng(37.2196184,127.1841511));
        routes.add(new LatLng(37.2195138,127.1838280));
        routes.add(new LatLng(37.2194862,127.1836185));
        routes.add(new LatLng(37.2194853,127.1836084));
        routes.add(new LatLng(37.2194667,127.1833988));
        routes.add(new LatLng(37.2194663,127.1832489));
        routes.add(new LatLng(37.2195019,127.1830661));
        routes.add(new LatLng(37.2195206,127.1829950));
        routeInfo.put("제3공학관_시내", routes);

    }

    public static StationInfo getInstance() {
        if(instance == null) {
            instance = new StationInfo();
        }
        return instance;
    }

    public LatLng getLatLng(String station) {
        return stationInfo.get(station);
    }

    public String[] getStationList_MjuStation() {
        return new String[]{"명지대", "상공회의소", "진입로", "명지대역", "진입로(명지대방향)", "이마트", "명진당", "제3공학관"};
    }

    public String[] getStationList_DownTown() {
        return new String[]{"명지대", "상공회의소", "진입로", "진입로(명지대방향)", "이마트", "동부경찰서", "용인시장", "중앙공영주차장", "제1공학관", "제3공학관"};
    }

    public ArrayList<LatLng> getPolyList_MjuStation() {
        String[] stations = new String[]{"명지대", "상공회의소", "진입로", "명지대역", "진입로(명지대방향)", "이마트", "명진당", "제3공학관"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for(String station: stations) {
            routes.addAll(routeInfo.get(station));
        }
        return routes;
    }

    public ArrayList<LatLng> getPolyList_DownTown() {
        String[] stations = new String[]{"명지대", "상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "진입로(명지대방향)_시내", "이마트", "제1공학관", "제3공학관_시내"};
        ArrayList<LatLng> routes = new ArrayList<>();
        for(String station: stations) {
            routes.addAll(routeInfo.get(station));
        }
        return routes;
    }
}
