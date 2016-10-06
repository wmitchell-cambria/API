#ifndef __CWS_COMMON_HPP_INCLUDED__
#define __CWS_COMMON_HPP_INCLUDED__

#pragma once




#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <stdarg.h>     /* va_list, va_start, va_arg, va_end */
#include <math.h>       /* pow */
#include <cstdio>
#include <cstdlib>


#include <algorithm>
#include <array>
#include <cerrno>
#include <chrono>
#include <climits>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <functional>
#include <initializer_list>
#include <iomanip>
#include <iostream>
#include <iterator>
#include <memory>
#include <sstream>
#include <string>
#include <thread>
#include <type_traits>
#include <vector>


#include "spdlog/spdlog.h"




// #include <endian.h> // Linux only? Not OSX?




// Nah, use std::fopen.
// errno_t fopen_s ( std::FILE* pFile, const char *filename, const char *mode ) {
// 	// pFile = std::freopen( filename, mode, pFile );
// 	pFile = std::fopen( filename, mode );
// 	return errno;
// }






// OS X, Linux, or Cygwin (without windows.h):
#ifndef _WIN32

typedef unsigned long UTF32;
using uint32 = UTF32;

typedef unsigned short UTF16;
using uint16 = UTF16;

typedef unsigned short UTF16;
using uint16 = UTF16;

#endif




// Produce a random number between 0 and limit, inclusive.
template<typename T>
T rand_lim(T limit) noexcept {
    T divisor = RAND_MAX / (limit+1);
    T retval;

    std::srand(std::time(0)); // seed random generator with current time.
    do {
        retval = rand() / divisor;
    } while (retval > limit);

    return retval;
}

std::string cws_format_time(const char *buf, const char *format, struct tm * a_tm) {
	std::stringstream ss;
	ss << std::put_time( a_tm, format );
	return ss.str();
}

std::string cws_format_time(const char *buf, struct tm * a_tm) {
	return cws_format_time(buf, "%c %Z", a_tm);
}

// ISO 8601 datetime format = "%Y-%m-%dT%H:%M:%SZ"
// ex: "2016-06-30T00:02:51.721Z"
std::tm parse_iso8601_date( const char * iso_8601 ) {
    std::tm tm = {};
    std::string s {iso_8601};
    const auto pos_1st_delim = s.find(".");
    const auto first_segment = (pos_1st_delim == std::string::npos) ? s : s.substr(0, pos_1st_delim);

    std::istringstream ss(first_segment);
    ss >> std::get_time(&tm, "%Y-%m-%dT%H:%M:%SZ");

    if ( ss.fail() ) {
        std::cerr << "Parse failed\n";
		throw std::runtime_error("FAILED TO PARSE DATETIME");
    } 

    return tm;
}




constexpr const size_t CWS_DOC_MAX_SEGMENTS = 384;

// Segment starting position and length.
struct cws_segment {
    unsigned char *  ptr;
    size_t           len;
};

struct cws_document {
    int  		file_doc_num;
	char   		doc_handle[31];
	uint16 		doc_segs;
	uint32 		doc_len;

	uint16      segment_count;
	cws_segment segments [CWS_DOC_MAX_SEGMENTS];  // Don't know a way around this one ...
	// std::vector<cws_segment> segments;

    // cws_segment * next_segment;

// 	char   		doc_auth[9];
// 	char   		doc_serv[9];
// 	doc_date	
// 	doc_time	
// 	lst_upd_id	
// 	lst_upd_ts	
// 	cmprs_prg	
// 	doc_name	
// 
// std::chrono::system_clock::time_point created_at;
// std::chrono::system_clock::time_point updated_at;
};







constexpr const size_t CHAR_ARRAY_LEN = 256;

std::shared_ptr<spdlog::logger> console;


static const std::array<unsigned char, CHAR_ARRAY_LEN> EBCDIC_TO_ASCII = {
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  91,  46,  60,  40,  43,  33, 
	38,   32,  32,  32,  32,  32,  32,  32, 
	32,   32,  93,  36,  42,  41,  59,  94, 
	45,   47,  32,  32,  32,  32,  32,  32, 
	32,   32, 124,  44,  37,  95,  62,  63, 
	32,   32,  32,  32,  32,  32, 238, 160, 
	161,  96,  58,  35,  64,  39,  61,  34, 
	230,  97,  98,  99, 100, 101, 102, 103, 
	104, 105, 164, 165, 228, 163, 229, 168, 
	169, 106, 107, 108, 109, 110, 111, 112, 
	113, 114, 170, 171, 172, 173, 174, 175, 
	239, 126, 115, 116, 117, 118, 119, 120, 
	121, 122, 224, 225, 226, 227, 166, 162, 
	236, 235, 167, 232, 237, 233, 231, 234, 
	158, 128, 129, 150, 132, 133, 148, 131, 
	123,  65,  66,  67,  68,  69,  70,  71, 
	72,   73, 149, 136, 137, 138, 139, 140, 
	125,  74,  75,  76,  77,  78,  79,  80, 
	81,   82, 141, 142, 143, 159, 144, 145, 
	92,   32,  83,  84,  85,  86,  87,  88, 
	89,   90, 146, 147, 134, 130, 156, 155, 
	48,   49,  50,  51,  52,  53,  54,  55, 
	56,   57, 135, 152, 157, 153, 151,  32
};

static const std::array<unsigned char, CHAR_ARRAY_LEN> ASCII_TO_EBCDIC = {
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*         	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*         	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*         	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*         	*/
	0x40,0x4F,0x7F,0x7B,0x5B,0x6C,0x50,0x7D,	/*  !"#$%&'	*/
	0x4D,0x5D,0x5C,0x4E,0x6B,0x60,0x4B,0x61,	/* ()*+,-./	*/
	0xF0,0xF1,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,	/* 01234567	*/
	0xF8,0xF9,0x7A,0x5E,0x4C,0x7E,0x6E,0x6F,	/* 89:;<=>?	*/
	0x7C,0xC1,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,	/* @ABCDEFG	*/
	0xC8,0xC9,0xD1,0xD2,0xD3,0xD4,0xD5,0xD6,	/* HIJKLMNO	*/
	0xD7,0xD8,0xD9,0xE2,0xE3,0xE4,0xE5,0xE6,	/* PQRSTUVW	*/
	0xE7,0xE8,0xE9,0x4A,0xE0,0x5A,0x5F,0x6D,	/* XYZ[\]^_	*/
	0x79,0x81,0x82,0x83,0x84,0x85,0x86,0x87,	/* `abcdefg	*/
	0x88,0x89,0x91,0x92,0x93,0x94,0x95,0x96,	/* hijklmno	*/
	0x97,0x98,0x99,0xA2,0xA3,0xA4,0xA5,0xA6,	/* pqrstuvw	*/
	0xA7,0xA8,0xA9,0xC0,0x6A,0xD0,0xA1,0x40,	/* xyz{|}~ 	*/
	0xB9,0xBA,0xED,0xBF,0xBC,0xBD,0xEC,0xFA,	/*          */
	0xCB,0xCC,0xCD,0xCE,0xCF,0xDA,0xDB,0xDC,	/*          */
	0xDE,0xDF,0xEA,0xEB,0xBE,0xCA,0xBB,0xFE,	/*          */
	0xFB,0xFD,0x7d,0xEF,0xEE,0xFC,0xB8,0xDD,	/*          */
	0x77,0x78,0xAF,0x8D,0x8A,0x8B,0xAE,0xB2,	/*          */
	0x8F,0x90,0x9A,0x9B,0x9C,0x9D,0x9E,0x9F,	/*          */
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*          */
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*	       	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*	       	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*	       	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*	       	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*	       	*/
	0xAA,0xAB,0xAC,0xAD,0x8C,0x8E,0x80,0xB6,	/*          */
	0xB3,0xB5,0xB7,0xB1,0xB0,0xB4,0x76,0xA0,	/*          */
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40,	/*        	*/
	0x40,0x40,0x40,0x40,0x40,0x40,0x40,0x40 	/*        	*/
};

inline unsigned char convert_ebcdic_to_ascii(unsigned char ascii) {
    return EBCDIC_TO_ASCII[ascii];
}

inline unsigned char convert_ascii_to_ebcdic(unsigned char ascii) {
    return ASCII_TO_EBCDIC[ascii];
}

inline bool is_alphanum_or_space(unsigned char c) {
	return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';
}

inline bool is_digit(unsigned char c) {
	return c >= '0' && c <= '9';
}

inline bool is_alphanum_or_space_ebcdic(unsigned char c) {
	return is_alphanum_or_space( convert_ebcdic_to_ascii(c) );
}

inline bool is_digit_ebcdic(unsigned char c) {
	return is_digit( convert_ebcdic_to_ascii(c) );
}

//
// Load file contents into a unique_ptr buffer and stores the number of bytes written in param array_size.
//
std::unique_ptr<unsigned char[]> load_file( const char *filename, size_t & array_size ) {
	
    std::ifstream in(filename, std::ios::in | std::ios::binary);
    if (in) {
        in.seekg(0, std::ios::end);
        array_size = in.tellg();
        array_size++;
        std::unique_ptr<unsigned char[]> contents = std::make_unique<unsigned char[]>(array_size);

        in.seekg(0, std::ios::beg);
        in.read((char*)contents.get(), array_size);
        in.close();
        return contents;
    }

    throw(errno);
}

//
// Load raw binary bytes from file into vector.
//
void load_file(std::vector<unsigned char>& v, const std::string& filename) {
    std::ifstream in(filename.c_str(), std::ios::in|std::ios::binary);

    // Get file size.
    std::streamsize new_size = 0;
    v.clear();

    if (in.seekg(0, std::ios::end).good()) 
    	new_size  = in.tellg();
    	
    if (in.seekg(0, std::ios::beg).good()) 
    	new_size -= in.tellg();

    // printf("\nload_file(): new_size=%d", new_size);

    // Read contents of the file into the vector.
    if ( v.capacity() < (size_t(new_size) + 1) ) {
        v.reserve(size_t(new_size) + 1);
    }

    if ( new_size > 0 ) {
    	// C++11/14 defines std::vector as a contiguous memory block, but you wouldn't know that from the interface.
    	// C++17 clearly designates which containers store data contigously.
    	
    	// PORTABILITY ERROR?? 
    	// Neither approach works on Apple LLVM version 6.1.0. -:(
    	// in.read( (char*)(&v[0]), new_size );
        // in.read( v.data(), new_size );
        
    	// printf("\nload_file(): IN FILE READ BLOCK: new_size=%d", new_size);
        std::copy((std::istreambuf_iterator<char>(in)), std::istreambuf_iterator<char>(), std::back_inserter(v));
    	in.close();
    }

    printf("\nload_file(): vector size=%d", v.size());
}

void save_file(const std::vector<unsigned char>& v, const std::string& filename) {
    std::ofstream file(filename.c_str(), std::ios::out|std::ios::binary);
    file.write(v.empty() ? 0 : (char*)&v[0], std::streamsize(v.size()));
}

//
// Write given char buffer to the file, overwriting the file, not append to it.
//
void save_file(const char * buf, int len, const std::string& filename) {
    std::ofstream file(filename.c_str(), std::ios::out|std::ios::binary);
    file.write( (char*)buf, std::streamsize(len) );
}

void save_file_append(const char * buf, int len, const std::string& filename) {
    std::ofstream file(filename.c_str(), std::ios::out|std::ios::binary|std::ios::ate);
    file.write( (char*)buf, std::streamsize(len) );
}


// TODO: 
void save_document(const cws_document & doc) {
//     std::ofstream file(filename.c_str(), std::ios::out|std::ios::binary);
//     file.write(v.empty() ? 0 : (char*)&v[0], std::streamsize(v.size()));
}

void FileCopy(const char * pszSrcFile, const char * pszDestFile, unsigned long nSkip) {
    std::ifstream  src(pszSrcFile,  std::ios::binary);
    
    if (nSkip > 0) {
    	src.seekg(nSkip - 1);
    }
    
    std::ofstream  dst(pszDestFile, std::ios::binary);
    dst << src.rdbuf();
}

void FileCopy(const char * pszSrcFile, const char * pszDestFile) {
	FileCopy(pszSrcFile, pszDestFile, 0);
}


#endif // __CWS_COMMON_HPP_INCLUDED__


























